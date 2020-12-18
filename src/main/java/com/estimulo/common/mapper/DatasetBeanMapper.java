package com.estimulo.common.mapper;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.springframework.stereotype.Component;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.DataSetList;
import com.tobesoft.xplatform.data.DataTypes;
import com.tobesoft.xplatform.data.PlatformData;

import com.estimulo.common.annotation.ColumnName;
import com.estimulo.common.annotation.Dataset;
import com.estimulo.common.annotation.Remove;


/* �뿊�뵆�쓽 Dataset -> TO �삉�뒗 TO -> Dataset */
@Component
public class DatasetBeanMapper {

	//1
    private <T> String getDataSetName(Class<T> classType) {
    	
    	// Dataset�뿉 �뼱�끂�뀒�씠�뀡�씠 �엳�쑝硫� true
        if(classType.isAnnotationPresent(Dataset.class)) {
        	
        	// Dataset �뼱�끂�뀒�씠�뀡�쓽 �씠由꾩쓣 諛섑솚
        	System.out.println("###"+classType.getAnnotation(Dataset.class).name()+"#");
            return classType.getAnnotation(Dataset.class).name();
        }else {
        	// �씠�빐 x
            return "ds" + classType.getName().replace("Bean", "");
        }
    }
    
    
    /* 媛앹껜 �깮�꽦 */
    private <T> T getBean(DataSet dataset, Class<T> classType, int rowIndex) throws Exception {
        T bean = classType.newInstance();
        Method[] methods = classType.getDeclaredMethods(); //TO 硫붿냼�뱶瑜� �떞�쓬 SET�씠�윴嫄� GET
        Method statusMethod = classType.getMethod("setStatus", String.class); //硫붿냼�뱶 �씠由꾩씠 SETSTATUS  瑜� ��蹂��닔�뿉�떞�쓬
        String rowType = null;
        
        switch(dataset.getRowTypeName(rowIndex)){//rowindex 泥ル쾲夷�
        	
        	case "inserted" :
        		rowType = "insert";
        		System.out.println("dd::"+dataset.getRowTypeName(rowIndex));
        		break;
        	case "updated" :
        		rowType = "update";
        		break;
        }
        statusMethod.invoke(bean, rowType); //由ы뵆�젆�뀡�븿 status �긽�깭媛�
        for(Method method : methods) {
            if(method.getName().startsWith("set")) { //set�쑝濡� �떆�옉�릺�뒗 而щ읆紐� �꽦
                String columnName = getColumnName(method);
                System.out.println("		@DatasetBeanMapper.getBean.columnName: "+columnName);
                if(columnName != null) {
                    Object columnValue = dataset.getObject(rowIndex, columnName); //getObject 吏��젙�맂 �뿴�쓾諛㏃쓣 java媛앹껜濡� 蹂��솚
                    System.out.println("		@DatasetBeanMapper.getBean.columnValue: "+columnValue);
                    if(columnValue != null)
                        method.invoke(bean, columnValue);
                }
            }
        }
        return bean;
    }
    

	/* Dataset -> TO �뿬�윭媛� */
    public <T> List<T> datasetToBeans(PlatformData inData, Class<T> classType) throws Exception {
        
    	String datasetName = getDataSetName(classType);
        DataSet dataset = inData.getDataSet(datasetName);

        List<T> beanList = new ArrayList<T>();
        T bean = null;
        int rowCount = dataset.getRowCount();
        for(int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
        	
        	// Dataset�뿉 �엳�뒗 �뻾�쓽 媛쒖닔留뚰겮 諛섎났臾� �떎�뻾�븯�뿬 �젣�꼫由� type�쓽 Dataset媛앹껜 �깮�꽦
            bean = getBean(dataset, classType, rowIndex);
            beanList.add(bean);
        }

        rowCount = dataset.getRemovedRowCount();
        for(int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            bean = getDeletedBean(dataset, classType, rowIndex);
            beanList.add(bean);
        }
        return beanList;
    }

    //1
    public <T> T datasetToBean(PlatformData inData, Class<T> classType) throws Exception {
        T bean = null;
        String datasetName = getDataSetName(classType);//�뼱�끂�뀒�씠�뀡 �씠由꾨컲�솚
        System.out.println("datasetName: " +datasetName);
        DataSet dataset = inData.getDataSet(datasetName);//�뼱�끂�뀒�씠�뀡 �떞�쓬

        if(dataset.getRemovedRowCount() == 0)  //由щТ釉� 移댁슫�듃媛� �쁺�씠硫�
            bean = getBean(dataset, classType, 0);
        else
            bean = getDeletedBean(dataset, classType, 0);
        return bean;
    }

    public <T> void beansToDataset(PlatformData outData, List<T> beanList, Class<T> classType) throws Exception {
        Map<String, String> nameMap = new HashMap<String, String>();
       System.out.println("?");
        System.out.println(outData.getDataSetList()+"??zzz");
        DataSetList datasetList = outData.getDataSetList();
        System.out.println("??zsdadqw"+datasetList);
        String datasetName = getDataSetName(classType);
        System.out.println(datasetName+"zzzzzzdd");
        DataSet dataset = new DataSet(datasetName);
        datasetList.add(dataset);
        
        Field[] fields = classType.getDeclaredFields();
        for(Field field : fields)
            setColumnName(dataset, nameMap, field);
        for(T bean : beanList)
            setColumnValue(dataset, nameMap, bean);
    }


    public <T> void beanToDataset(PlatformData outData, T bean, Class<T> classType) throws Exception {
        Map<String, String> nameMap = new HashMap<String, String>();
        DataSetList datasetList = outData.getDataSetList();

        String datasetName = getDataSetName(classType);
        DataSet dataset = new DataSet(datasetName);

        datasetList.add(dataset);

        if(bean != null) {
        	Field[] fields = classType.getDeclaredFields();
            for(Field field : fields)
                setColumnName(dataset, nameMap, field);
            setColumnValue(dataset, nameMap, bean);
        }
    }

    public void mapToDataset(PlatformData outData, List<Map<String, Object>> mapList, String datasetName) throws Exception {
        DataSetList datasetList = outData.getDataSetList();
        DataSet dataset = new DataSet(datasetName);
        datasetList.add(dataset);

        for(String key : mapList.get(0).keySet()) {
            String columnName = key.toLowerCase();
            dataset.addColumn(columnName, DataTypes.STRING, 256);
        }

        int rowIndex = 0;
        for(Map<String, Object> map : mapList) {
            rowIndex = dataset.newRow();
            for(String key : map.keySet()) {
                Object columnValue = map.get(key);
                dataset.set(rowIndex, key.toLowerCase(), columnValue);
            }
        }
    }

    public List<Map<String, Object>> datasetToMap(PlatformData inData, String datasetName) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<>();

        DataSet dataset = inData.getDataSet(datasetName);
        int rowCount = dataset.getRowCount();
        for(int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", dataset.getRowTypeName(rowIndex));

            for(int colIndex = 0; colIndex < dataset.getColumnCount(); colIndex++) {
                String key = dataset.getColumn(colIndex).getName();
                Object value = dataset.getObject(rowIndex, key);
                map.put(formattingToCamel(key), value);
            }
            mapList.add(map);
        }

        rowCount = dataset.getRemovedRowCount();
        for(int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", dataset.getRowTypeName(rowIndex));

            for(int colIndex = 0; colIndex < dataset.getColumnCount(); colIndex++) {
                String key = dataset.getColumn(colIndex).getName();
                Object value = dataset.getObject(rowIndex, key);
                map.put(formattingToCamel(key), value);
            }
            mapList.add(map);
        }
        return mapList;
    }



    private String getColumnName(Method method) {
        String columnName = null;
        Annotation[] annotations = method.getAnnotations(); //set �뼱�끂�뀒�씠�뀡 諛곗뿴�뿉�떞�쓬
        for (Annotation annotation : annotations) {
            if (annotation instanceof ColumnName) {  
                String annotaionName = ((ColumnName) annotation).name();
                	System.out.println(annotaionName+":::::annotaionName");
                columnName = annotaionName;
            }
        }
        if (annotations.length == 0) 
            columnName = formattingToSnake(method.getName());
        return columnName;
    }

    private void setColumnName(DataSet dataset, Map<String, String> nameMap, Field field) {
        	ColumnName column = field.getAnnotation(ColumnName.class);
            Remove remove = field.getAnnotation(Remove.class);

            if(column != null) {
                dataset.addColumn(column.name(), getDataType(field));
                nameMap.put(column.name(), field.getName());
            } else if(column == null && remove == null) {
                String columnName = formattingToSnake(field.getName());
                dataset.addColumn(columnName, getDataType(field));
                nameMap.put(columnName, field.getName());
            }
        
    }

    private <T> void setColumnValue(DataSet dataset, Map<String, String> nameMap, T bean) throws Exception {
        int rowIndex = dataset.newRow();

        for(String columnName : nameMap.keySet()) {
            String fieldName = nameMap.get(columnName);
            Field value = bean.getClass().getDeclaredField(fieldName.trim());
            value.setAccessible(true);									// Priavte 濡� �릺�뼱�엳�뒗 媛앹껜�뿉 �젒洹쇳븯湲� �쐞�빐�꽌 �궗�슜
            dataset.set(rowIndex, columnName, value.get(bean));			// 媛믪쓣 �뼸湲� �쐞�빐�꽌 �궗�슜 
        }
    }

    
    private <T> T getDeletedBean(DataSet dataset, Class<T> classType, int rowIndex) throws Exception {
        T bean = classType.newInstance();
        Method[] methods = classType.getDeclaredMethods();
        Method statusMethod = classType.getMethod("setStatus", String.class);
        statusMethod.invoke(bean, "delete");
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                String columnName = getColumnName(method);
                if (columnName != null) {
                    Object columnValue = dataset.getRemovedData(rowIndex, columnName);
                    if (columnValue != null)
                        method.invoke(bean, columnValue);
                }
            }
        }
        return bean;
    }
    private int getDataType(Field field) {
        Class<?> returnType = field.getType();
        if(returnType == Date.class)
            return DataTypes.DATE;
        else if(returnType == String.class)
            return DataTypes.STRING;
        else if(returnType == int.class || returnType == boolean.class)
            return DataTypes.INT;
        else if(returnType == BigDecimal.class)
            return DataTypes.BIG_DECIMAL;
        else if(returnType == double.class )
            return DataTypes.DOUBLE;
        else if(returnType == byte[].class)
            return DataTypes.BLOB;
        else
            return DataTypes.NULL;
    }

    private String formattingToSnake(String name) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";


        if(name.startsWith("set") || name.startsWith("get"))
            name = name.substring(3);
        // aA -> a_A
        name = name.replaceAll(regex, replacement);
        // return A_A
        return name.toUpperCase();

    }

    private String formattingToCamel(String name) {

        if(name.startsWith("set") || name.startsWith("get"))
            name = name.substring(3);
        String camel = WordUtils.capitalizeFully(name, new char[]{'_'}).replaceAll("_", "");
        return camel.substring(0, 1).toLowerCase() + camel.substring(1);
    }
}
