/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.el.ELContext;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named(value = "transItemReader")
public class TransItemReader extends AbstractItemReader {

    private BufferedReader reader;
    @Inject
    private JobContext jobContext;
    InputStream inputStream;
    private File file;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
        String resourceName = (String) jobParameters.get("filePath"); 
        file = new File(resourceName);
        InputStream targetStream = new FileInputStream(file);
        reader = new BufferedReader(
                new InputStreamReader(targetStream));
        reader.readLine();

    }

    @Override
    public String readItem() {
        try {

            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(TransItemReader.class.getName()).log(Level.ALL.SEVERE, null, ex);
        }
        return null;
    }
}
