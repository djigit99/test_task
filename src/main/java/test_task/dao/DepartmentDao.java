package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Department;

import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
    //TODO Get a list of department IDS where the number of employees doesn't exceed 3 people
    @Query(
            value = "SELECT id FROM public.department AS d " +
                    "WHERE (SELECT COUNT(*) FROM public.employee AS e WHERE d.id = e.department_id) < 4",
            nativeQuery = true)
    List<Long> findAllWhereDepartmentDoesntExceedThreePeople();

    //TODO Get a list of departments IDs with the maximum total salary of employees
    @Query(
            value = "SELECT emp.department_id FROM public.employee AS emp " +
                    "GROUP BY emp.department_id " +
                    "HAVING SUM(emp.salary) = " +
                    "(SELECT SUM(e.salary) AS sum FROM public.employee AS e " +
                    "GROUP BY e.department_id " +
                    "ORDER BY sum DESC " +
                    "LIMIT 1)",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
