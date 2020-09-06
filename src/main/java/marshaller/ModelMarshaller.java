package marshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.reflections.Reflections;

import com.fasterxml.jackson.databind.ObjectMapper;

import adapter.Amount;
import adapter.ChemicalElement;
import adapter.ChemicalProcess;
import model.chemical.items.Energy;
import model.chemical.items.Water;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

public class ModelMarshaller {

	public static void main(String[] args) throws Exception {
		
		String author="Alessandro Siletto";
		String version="1.0.0";
		String outputDir="C:\\Users\\Alessandro\\eclipse-workspace\\mindustry-chemical-factory\\mods\\";
		
		new File(outputDir+"/"+"docs/process").mkdirs();
		new File(outputDir+"/"+"docs/element").mkdirs();
		IOUtils.write("", new FileOutputStream(new File(outputDir+"docs/index.md")),"UTF-8");
		
		marshall("ChemicalPlant", author, version, outputDir , "model.chemical");
		marshall("FossilFuel", author, version, outputDir, "model.fossil");

	}
	
	public static void marshall(String modelName, String author, String version, String outputDir, String packageName) throws Exception {

		new File(outputDir+modelName+"/"+"bundles").mkdirs();
		new File(outputDir+modelName+"/"+"content/blocks").mkdirs();
		new File(outputDir+modelName+"/"+"content/items").mkdirs();
		new File(outputDir+modelName+"/"+"content/liquids").mkdirs();
		new File(outputDir+modelName+"/"+"content/mechs").mkdirs();
		new File(outputDir+modelName+"/"+"content/units").mkdirs();
		new File(outputDir+modelName+"/"+"content/zones").mkdirs();
		new File(outputDir+modelName+"/"+"maps").mkdirs();
		new File(outputDir+modelName+"/"+"scripts").mkdirs();
		new File(outputDir+modelName+"/"+"sounds").mkdirs();
		new File(outputDir+modelName+"/"+"sprites/blocks").mkdirs();
		new File(outputDir+modelName+"/"+"sprites/items").mkdirs();
		new File(outputDir+modelName+"/"+"sprites/mechs").mkdirs();
		new File(outputDir+modelName+"/"+"sprites/units").mkdirs();
		new File(outputDir+modelName+"/"+"sprites/zones").mkdirs();
		new File(outputDir+modelName+"/"+"").mkdirs();
		
		String modTemplate = IOUtils.toString(new FileInputStream(new File("templates/mod.template.hjson")),"UTF-8");
		modTemplate = modTemplate.replaceAll("\\$\\{name\\}", modelName);
		modTemplate = modTemplate.replaceAll("\\$\\{author\\}", author);
		modTemplate = modTemplate.replaceAll("\\$\\{version\\}", version);		
		IOUtils.write(modTemplate, new FileOutputStream(new File(outputDir+modelName+"/mod.hjson")),"UTF-8");
		
		Reflections reflections = new Reflections(packageName);
		Set<Class<? extends ChemicalProcess>> processes = reflections.getSubTypesOf(ChemicalProcess.class);
		
		Set<Class> items = new HashSet<Class>();
		
		
		updateIndexFile(processes, outputDir, modelName);

		//for each process
		for (Class<? extends ChemicalProcess> process : processes) {
			
			String processName = normalize(process.getSimpleName()," ");
			String processId = normalize(process.getSimpleName(),"-").toLowerCase();
			System.out.println(processName + " | " + processId);
			
			ChemicalProcess instance = process.newInstance();
			writeProcessPage(instance, outputDir, modelName);

			List<Amount> consumes = instance.getConsumes();
			List<Amount> produces = instance.getProduces();
			
			Double energy = getEnergy(consumes);			
			
			List<Map<String,Object>> consumesData = new ArrayList<Map<String,Object>>();
			for (Amount consume : consumes) {
				if(consume.getElement().getClass().equals(Energy.class)){
					//dont add to output
				} else if(consume.getElement().getClass().equals(Water.class)){
					//dont add to output
				}else {
					items.add(consume.getElement().getClass());
					
					Map<String,Object> item = new HashMap<String,Object>();
					ChemicalElement element = consume.getElement().getClass().newInstance();
					String elementName = normalize(element.getClass().getSimpleName(),"-").toLowerCase();
					item.put("item", elementName);
					item.put("amount", consume.getValue());
					consumesData.add(item);
				}
			}
			
			List<Map<String,Object>> producesData = new ArrayList<Map<String,Object>>();
			for (Amount produce : produces) {
				if(produce.getElement().getClass().equals(Energy.class)){
					//dont add to output
				} else if(produce.getElement().getClass().equals(Water.class)){
					//dont add to output
				}else {
					items.add(produce.getElement().getClass());
					
					Map<String,Object> item = new HashMap<String,Object>();
					ChemicalElement element = produce.getElement().getClass().newInstance();
					String elementName = normalize(element.getClass().getSimpleName(),"-").toLowerCase();
					item.put("item", elementName);
					item.put("amount", produce.getValue());
					
					System.out.println("BLOCK: "+processName+"-separator PRODUCES " + elementName+ " value: " + produce.getValue());
					
					
					producesData.add(item);
				}
			}
			
			String producesStr = new ObjectMapper().writeValueAsString(producesData);
			String consumesStr = new ObjectMapper().writeValueAsString(consumesData);
			
			//generate a GenericCrafter
			String crafterTemplate = IOUtils.toString(new FileInputStream(new File("templates/GenericCrafter.template.json")),"UTF-8");
			crafterTemplate = crafterTemplate.replaceAll("\\$\\{processName\\}", processName);
			crafterTemplate = crafterTemplate.replaceAll("\\$\\{processId\\}", processId);
			crafterTemplate = crafterTemplate.replaceAll("\\$\\{consumes\\}", consumesStr);
			crafterTemplate = crafterTemplate.replaceAll("\\$\\{size\\}", "3");
			IOUtils.write(crafterTemplate, new FileOutputStream(new File(outputDir+modelName+"/"+"content/blocks/"+processId+"-crafter.json")),"UTF-8");
			
			//generate the intermediate item
			String itemTemplate = IOUtils.toString(new FileInputStream(new File("templates/liquid.template.json")),"UTF-8");
			itemTemplate = itemTemplate.replaceAll("\\$\\{name\\}", processName+" Intermediate");
			IOUtils.write(itemTemplate, new FileOutputStream(new File(outputDir+modelName+"/"+"content/liquids/"+processId+"-intermediate.json")),"UTF-8");
			
			//generate a Separator
			String separatorTemplate = IOUtils.toString(new FileInputStream(new File("templates/Separator.template.json")),"UTF-8");
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{processName\\}", processName);
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{processId\\}", processId);
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{powerComsumption\\}", ""+energy);
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{liquidConsumption\\}", "water");
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{liquidConsumptionValue\\}", "1");
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{size\\}", "3");
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{produces\\}", producesStr);
			IOUtils.write(separatorTemplate, new FileOutputStream(new File(outputDir+modelName+"/"+"content/blocks/"+processId+"-separator.json")),"UTF-8");

			generateProcessImages(outputDir, modelName, processId);
		}
		
		//for each item
		for (Class classItem : items) {
			ChemicalElement element = (ChemicalElement)classItem.newInstance();
			writeElementPage(element, outputDir, modelName);
			String elementName = normalize(element.getClass().getSimpleName()," ");
			String elementId = normalize(element.getClass().getSimpleName(),"-").toLowerCase();
			System.out.println(" item: " + elementName);
			String itemTemplate = IOUtils.toString(new FileInputStream(new File("templates/item.template.hjson")),"UTF-8");
			itemTemplate = itemTemplate.replaceAll("\\$\\{name\\}", elementName);
			IOUtils.write(itemTemplate, new FileOutputStream(new File(outputDir+modelName+"/"+"content/items/"+elementId+".hjson")),"UTF-8");
			generateItemImage(outputDir, modelName, elementId);
		}
		
		
		
		File generatedDirectory=new File(outputDir+modelName);
		generatedDirectory.delete();
		
		
		ZipParameters params = new ZipParameters();
		
		ZipFile zip = new ZipFile(modelName+".zip");
		File[] files = generatedDirectory.listFiles();
		
		for (File file : files) {
			if(file.isFile())
				zip.addFile(file);
			
			if(file.isDirectory())
				zip.addFolder(file);
		}
		
		
		
	}
	
	private static void updateIndexFile(Set<Class<? extends ChemicalProcess>> processes, String outputDir, String modelName) throws FileNotFoundException, IOException {
		String itemTemplate = IOUtils.toString(new FileInputStream(new File("templates/index.template.md")),"UTF-8");
		
		StringBuffer processesStr = new StringBuffer();
		for (Class<? extends ChemicalProcess> process : processes) {			
			String processName = normalize(process.getSimpleName()," ");
			String processId = normalize(process.getSimpleName(),"-").toLowerCase();
			processesStr.append("- ["+processName+"](process/"+processId+")\n");
		}
		itemTemplate = itemTemplate.replaceAll("\\$\\{modelName\\}", modelName);
		itemTemplate = itemTemplate.replaceAll("\\$\\{processes\\}", processesStr.toString());

		FileUtils.write(new File(outputDir+"/docs/index.md"), itemTemplate, true);
	}

	private static void writeProcessPage(ChemicalProcess process, String outputDir, String modelName) throws FileNotFoundException, IOException {
		String itemTemplate = IOUtils.toString(new FileInputStream(new File("templates/process.template.md")),"UTF-8");
		String processName = normalize(process.getClass().getSimpleName()," ");
		String processId = normalize(process.getClass().getSimpleName(),"-").toLowerCase();
		itemTemplate = itemTemplate.replaceAll("\\$\\{processName\\}", processName);
		
		//consume/produces
		StringBuffer consumes = new StringBuffer();
		for (Amount amount : process.getConsumes()) {
			String name = normalize(amount.getElement().getClass().getSimpleName()," ");

			consumes.append(" - "+name+" "+amount.getValue()+" "+amount.getUnit()+"\n");
		}
		itemTemplate = itemTemplate.replaceAll("\\$\\{consumes\\}", consumes.toString());

		StringBuffer produces = new StringBuffer();
		for (Amount amount : process.getProduces()) {
			String name = normalize(amount.getElement().getClass().getSimpleName()," ");

			produces.append(" - "+name+" "+amount.getValue()+" "+amount.getUnit()+"\n");
		}
		itemTemplate = itemTemplate.replaceAll("\\$\\{produces\\}", produces.toString());

		IOUtils.write(itemTemplate, new FileOutputStream(new File(outputDir+"/docs/process/"+processId+".md")),"UTF-8");
	}

	private static void writeElementPage(ChemicalElement element, String outputDir, String modelName) throws FileNotFoundException, IOException {
		String itemTemplate = IOUtils.toString(new FileInputStream(new File("templates/element.template.md")),"UTF-8");
		String elementName = normalize(element.getClass().getSimpleName()," ");
		String elementId = normalize(element.getClass().getSimpleName(),"-").toLowerCase();
		itemTemplate = itemTemplate.replaceAll("\\$\\{elementName\\}", elementName);
		IOUtils.write(itemTemplate, new FileOutputStream(new File(outputDir+"/docs/element/"+elementId+".md")),"UTF-8");
	}

	private static void generateItemImage(String outputDir, String modelName, String elementId) throws IOException {
		File custom = new File("templates/sprites/"+elementId+".png");
		File inputFile = new File("templates/item.png");
		if(custom.exists())
			inputFile = custom;

		FileUtils.copyFile(inputFile, new File(outputDir+modelName+"/"+"sprites/items/"+elementId+".png"));
	}

	public static void generateProcessImages(String outputDir, String modelName, String processId) throws IOException {
		File custom = new File("templates/sprites/"+processId+"-crafter.png");
		File inputFile = new File("templates/GenericCrafter.png");
		if(custom.exists())
			inputFile = custom;
		FileUtils.copyFile(inputFile, new File(outputDir+modelName+"/"+"sprites/blocks/"+processId+"-crafter.png"));

		custom = new File("templates/sprites/"+processId+"-separator.png");
		inputFile = new File("templates/Separator.png");
		if(custom.exists())
			inputFile = custom;
		FileUtils.copyFile(inputFile, new File(outputDir+modelName+"/"+"sprites/blocks/"+processId+"-separator.png"));

		generateItemImage(outputDir, modelName, processId+"-intermediate");
	}
	
	private static Double getEnergy(List<Amount> consumes) {
		Double ret = 0d;
		for (Amount amount : consumes) {
			if(amount.getElement().getClass().equals(Energy.class)){
				System.out.println("amount.getValue: " + amount.getValue());
				ret += amount.getValue();
			}
		}
		System.out.println("ret: " + ret);
		return ret;
	}

	//name normalizer
	static final String regex = "(?=[A-Z][a-z])";
    static final Pattern pattern = Pattern.compile(regex);

	public static String normalize(String string, String subst){
        final Matcher matcher = pattern.matcher(string);
        final String result = matcher.replaceAll(subst);
        return result.substring(1);
	}

}
