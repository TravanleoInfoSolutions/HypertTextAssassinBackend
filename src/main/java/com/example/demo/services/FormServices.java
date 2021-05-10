package com.example.demo.services;

import com.example.demo.domain.Form;
import com.example.demo.repo.FormRepo;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.fields.PdfField;
import com.spire.pdf.widget.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service(value = "FormServices")
public class FormServices {

    @Autowired
    private FormRepo formRepo;

    private static String UPLOADED_FOLDER = "C:/Users/Aswathy TD/Documents/";
    public List pdfRead( MultipartFile file) {

        List list = null;
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            //Load PDF document
            PdfDocument pdf = new PdfDocument();
            pdf.loadFromFile(String.valueOf(path));

            //Get form fields
            PdfFormWidget formWidget = (PdfFormWidget) pdf.getForm();

            //Loop through the form field widget collection and extract the value of each field
            Form formDto = null;
            list = new ArrayList();
            for (int i = 0; i < formWidget.getFieldsWidget().getCount(); i++) {

                formDto = new Form();
                PdfField field = (PdfField) formWidget.getFieldsWidget().getList().get(i);

                if (field instanceof PdfTextBoxFieldWidget) {
                    PdfTextBoxFieldWidget textBoxField = (PdfTextBoxFieldWidget) field;
                    //Get text of textbox
                    String filed = textBoxField.getName();
                    String text = textBoxField.getText();

                    formDto.setName(filed);
                    formDto.setValue(text);
                    formRepo.save(formDto);
                if (field instanceof PdfListBoxWidgetFieldWidget){

                PdfListBoxWidgetFieldWidget listBoxField = (PdfListBoxWidgetFieldWidget)field;

                PdfListWidgetItemCollection items = listBoxField.getValues();

                String selectedValue = listBoxField.getSelectedValue();
                //Get text of textbox
                String key= listBoxField.getName();

                formDto.setName(key);
                formDto.setValue(selectedValue);
                formRepo.save(formDto);

            }

            if (field instanceof PdfComboBoxWidgetFieldWidget){

                PdfComboBoxWidgetFieldWidget comBoxField = (PdfComboBoxWidgetFieldWidget)field ;

                //Get values of comboBox
                PdfListWidgetItemCollection items = comBoxField.getValues();


                //Get selected value
                String selectedValue = comBoxField.getSelectedValue();
                String key=comBoxField.getName();
                formDto.setName(key);
                formDto.setValue(selectedValue);
                formRepo.save(formDto);
            }

            if (field instanceof PdfRadioButtonListFieldWidget){

                PdfRadioButtonListFieldWidget radioBtnField = (PdfRadioButtonListFieldWidget)field;
                //Get value of radio button
                String value = radioBtnField.getValue();
                String key=radioBtnField.getName();
                formDto.setName(key);
                formDto.setValue(value);
                formRepo.save(formDto);

            }

                }
                list.add(formDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }




}
