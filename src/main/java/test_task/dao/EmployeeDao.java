package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    @Query(
            value = "SELECT * FROM public.employee AS e " +
                    "WHERE e.salary > " +
                    "(SELECT salary FROM public.employee as b WHERE e.boss_id = b.id)",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    @Query(
            value = "SELECT * FROM public.employee AS e " +
                    "WHERE e.salary = " +
                        "(SELECT MAX(co.salary) FROM public.employee as co WHERE e.department_id = co.department_id)",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    @Query(
            value = "SELECT * FROM public.employee AS e " +
                    "WHERE e.boss_id IS NULL OR e.department_id != " +
                    "(SELECT department_id FROM public.employee as b WHERE e.boss_id = b.id)",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
