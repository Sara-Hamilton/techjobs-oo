package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute(job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        // checks for Validation errors, redisplays the form with error messages if errors exist
        if(errors.hasErrors()){
            return "new-job";
        } else {

            // creates a new empty Job object
            Job newJob = new Job();

            // populates job object with input from the form
            newJob.setName(jobForm.getName());
            newJob.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
            newJob.setLocation(new Location(jobForm.getLocation()));
            newJob.setPositionType(new PositionType(jobForm.getPositionType()));
            newJob.setCoreCompetency(new CoreCompetency(jobForm.getCoreCompetency()));

            // adds new Job object to the jobData list
            jobData.add(newJob);

            //gets the job ID
            int id = newJob.getId();

            // displays job with /job?id=jobIdNumber in the URL
            return "redirect:/job?id=" + id;
        }
    }
}
