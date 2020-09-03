package marshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import chemical.model.items.Energy;
import chemical.model.items.Water;
import mindustry.model.Amount;
import mindustry.model.ChemicalElement;
import mindustry.model.ChemicalProcess;

public class ModelMarshaller {

	public static void main(String[] args) throws Exception {
		
		String modelName="ChemicalPlant";
		String author="Alessandro Siletto";
		String version="1.0.0";
		String outputDir="D:/Desktop 5/semanticfactory/";
		
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
		
		String modTemplate = IOUtils.toString(new FileInputStream(new File("mod.template.hjson")),"UTF-8");
		modTemplate = modTemplate.replaceAll("\\$\\{name\\}", modelName);
		modTemplate = modTemplate.replaceAll("\\$\\{author\\}", author);
		modTemplate = modTemplate.replaceAll("\\$\\{version\\}", version);		
		IOUtils.write(modTemplate, new FileOutputStream(new File(outputDir+modelName+"/mod.hjson")),"UTF-8");
		
		Reflections reflections = new Reflections("chemical.model");
		Set<Class<? extends ChemicalProcess>> processes = reflections.getSubTypesOf(ChemicalProcess.class);
		
		Set<Class> items = new HashSet<Class>();
		
		//for each process
		for (Class<? extends ChemicalProcess> process : processes) {
			String processName = normalize(process.getSimpleName()," ");
			String processId = normalize(process.getSimpleName(),"-").toLowerCase();
			System.out.println(processName + " | " + processId);
			
			ChemicalProcess instance = process.newInstance();
			List<Amount> consumes = instance.getConsumes();
			List<Amount> produces = instance.getProduces();
			List<Amount> waste = instance.getProduces();
			
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
			
			List<Amount> output = new ArrayList<Amount>();
			output.addAll(produces);
			output.addAll(waste);
			List<Map<String,Object>> producesData = new ArrayList<Map<String,Object>>();
			for (Amount produce : output) {
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
					producesData.add(item);
				}
			}
			
			String producesStr = new ObjectMapper().writeValueAsString(producesData);
			String consumesStr = new ObjectMapper().writeValueAsString(consumesData);
			
			//generate a GenericCrafter
			String crafterTemplate = IOUtils.toString(new FileInputStream(new File("GenericCrafter.template.json")),"UTF-8");
			crafterTemplate = crafterTemplate.replaceAll("\\$\\{processName\\}", processName);
			crafterTemplate = crafterTemplate.replaceAll("\\$\\{processId\\}", processId);
			crafterTemplate = crafterTemplate.replaceAll("\\$\\{consumes\\}", consumesStr);
			IOUtils.write(crafterTemplate, new FileOutputStream(new File(outputDir+modelName+"/"+"content/blocks/"+processId+"-crafter.json")),"UTF-8");
			FileUtils.copyFile(new File("empty3.png"), new File(outputDir+modelName+"/"+"sprites/blocks/"+processId+"-crafter.png"));
			
			//generate the intermediate item
			String itemTemplate = IOUtils.toString(new FileInputStream(new File("liquid.template.json")),"UTF-8");
			itemTemplate = itemTemplate.replaceAll("\\$\\{name\\}", processName+" Intermediate");
			IOUtils.write(itemTemplate, new FileOutputStream(new File(outputDir+modelName+"/"+"content/liquids/"+processId+"-intermediate.json")),"UTF-8");
			FileUtils.copyFile(new File("item.png"), new File(outputDir+modelName+"/"+"sprites/items/"+processId+"-intermediate.png"));

			
			//generate a Separator
			String separatorTemplate = IOUtils.toString(new FileInputStream(new File("Separator.template.json")),"UTF-8");
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{processName\\}", processName);
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{processId\\}", processId);
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{powerComsumption\\}", ""+energy);
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{liquidConsumption\\}", "water");
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{liquidConsumptionValue\\}", "1");
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{size\\}", "3");
			separatorTemplate = separatorTemplate.replaceAll("\\$\\{produces\\}", producesStr);
			IOUtils.write(separatorTemplate, new FileOutputStream(new File(outputDir+modelName+"/"+"content/blocks/"+processId+"-separator.json")),"UTF-8");
			FileUtils.copyFile(new File("empty3.png"), new File(outputDir+modelName+"/"+"sprites/blocks/"+processId+"-separator.png"));
		}
		
		//for each item
		for (Class classItem : items) {
			ChemicalElement element = (ChemicalElement)classItem.newInstance();
			String elementName = normalize(element.getClass().getSimpleName()," ");
			String elementId = normalize(element.getClass().getSimpleName(),"-").toLowerCase();
			System.out.println(" item: " + elementName);
			String itemTemplate = IOUtils.toString(new FileInputStream(new File("item.template.hjson")),"UTF-8");
			itemTemplate = itemTemplate.replaceAll("\\$\\{name\\}", elementName);
			IOUtils.write(itemTemplate, new FileOutputStream(new File(outputDir+modelName+"/"+"content/items/"+elementId+".hjson")),"UTF-8");
			FileUtils.copyFile(new File("item.png"), new File(outputDir+modelName+"/"+"sprites/items/"+elementId+".png"));
		}

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
