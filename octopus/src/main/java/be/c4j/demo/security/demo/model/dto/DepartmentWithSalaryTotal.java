/*
 * Copyright 2014-2016 Rudy De Busscher (www.c4j.be)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package be.c4j.demo.security.demo.model.dto;

import be.c4j.demo.security.demo.model.Department;

/**
 *
 */
public class DepartmentWithSalaryTotal extends Department {

    private double salaryTotal;

    public DepartmentWithSalaryTotal(Department department, double salaryTotal) {
        setId(department.getId());
        setName(department.getName());
        setManager(department.getManager());
        this.salaryTotal = salaryTotal;
    }

    public double getSalaryTotal() {
        return salaryTotal;
    }

    public void setSalaryTotal(double salaryTotal) {
        this.salaryTotal = salaryTotal;
    }
}
