package DataStroge;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qazwq on 6/17/2017.
 */

public class StudentList implements Serializable {
    List<StudentUnit> list;
    List<String> names;
}
