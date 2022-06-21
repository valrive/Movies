package com.udemy.startingpointpersonal.utils

import com.udemy.startingpointpersonal.utils.annotation.Required

object Validator {


    /**
     * validate that the form fields annotated with @Required are not empty.
     * @param objectToValidate the object that contains all the form fields.
     * @throws NoSuchFieldException in case the field to be validated is not inside the object.
     * @throws RequiredFieldException in case there are empty fields that are mandatory
     */
    @Throws(
        RequiredFieldException::class,
        NoSuchFieldException::class
    )
    fun validateForm(objectToValidate: Any) {
        val fieldsRequired = mutableListOf<String>()
        val declaredFields = objectToValidate::class.java.declaredFields

        for (field in declaredFields) {
            for (annotation in field.annotations) {
                when(annotation) {
                    is Required -> {
                        field.isAccessible = true
                        val value = field.get(objectToValidate)

                        if (value == null || value.toString().isBlank()) {
                            fieldsRequired.add(field.name)
                        }
                    }
                }
            }
        }

        if(fieldsRequired.size > 0){
            throw RequiredFieldException(null, fieldsRequired)
        }
    }


}
