package uesocc.edu.sv.anf2017.batch;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jssbbonilla
 */
@Named(value = "frmBatch")
@ViewScoped
public class FrmBatchCuentas implements Serializable {

    private UploadedFile file;
    private File destFile;

    
    
    
    public void uploadFile(FileUploadEvent event) throws IOException {
        file = event.getFile();
        destFile = new File("file.csv");
        FileUtils.copyInputStreamToFile(file.getInputstream(), destFile);

        FacesMessage message = new FacesMessage("Archivo Adjuntado ", "");
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void process() {
        try {
            //STARTING JOB
            JobOperator jo = BatchRuntime.getJobOperator();
            Properties props = new Properties();
            props.setProperty("filePath", destFile.getAbsolutePath());

            long jid = jo.start("datafetch", props);
            JobExecution je = jo.getJobExecution(jid);

            FacesMessage message = new FacesMessage("Trabajo con id ", je.getExecutionId() + "Ha comenzado.");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

        }

    }
     public void processT() {
        try {
            //STARTING JOB
            JobOperator jo = BatchRuntime.getJobOperator();
            Properties props = new Properties();
            props.setProperty("filePath", destFile.getAbsolutePath());

            long jid = jo.start("transdata", props);
            JobExecution je = jo.getJobExecution(jid);

            FacesMessage message = new FacesMessage("Trabajo con id ", je.getExecutionId() + "Ha comenzado.");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

        }

    }


    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
