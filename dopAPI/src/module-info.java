/**
 * Module for ATU Software.
 * This module includes all the components used in the ATU examination paper approval system.
 * It includes various packages for enums, models, services, and the main application runner.
 */
open module atu.software {
	/**
     * Package containing enumerations used on the application.
     * This includes enums defining types and actions specific to the examination paper approval process.
     
	exports ie.atu.sw.enums;
	/**
     * Package containing the main application runner.
     * This includes the main class responsible for initialising and starting the application.
     */
	exports ie.atu.sw;

    /**
     * Package containing model classes representing different entities.
     * This includes classes such as ModuleInfo, Question, Examiner, and other specific models.
    */
	exports ie.atu.sw.model;
	/**
     * Package containing service interfaces and their implementations.
     * These services define the business logic for managing modules, exam papers, and examiners.
     */
	exports ie.atu.sw.services;
}
