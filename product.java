import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Course {
    private static int idCounter = 1; // Auto-generate ID
    private int id;
    private String courseName;
    private double fee;
    private String instructor;

    public Course(String courseName, double fee, String instructor) {
        this.id = idCounter++;
        this.courseName = courseName;
        this.fee = fee;
        this.instructor = instructor;
    }

    // using  getter and setter method 
    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}

public class CourseManagementApp extends JFrame {
    private List<Course> courseList = new ArrayList<>();
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public CourseManagementApp() {
        setTitle("Course Management Application");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add Buttons 

        JPanel buttonPanel = new JPanel();
        JButton addCourseButton = new JButton("Add Course");
        JButton viewCoursesButton = new JButton("View All Courses");
        JButton updateCourseButton = new JButton("Update Course");
        JButton deleteCourseButton = new JButton("Delete Course");
        JButton searchByInstructorButton = new JButton("Search by Instructor");

        buttonPanel.add(addCourseButton);
        buttonPanel.add(viewCoursesButton);
        buttonPanel.add(updateCourseButton);
        buttonPanel.add(deleteCourseButton);
        buttonPanel.add(searchByInstructorButton);
        
        add(buttonPanel, BorderLayout.NORTH);

        // Table Setup
        
        String[] columns = {"ID", "Course Name", "Fee", "Instructor"};
        tableModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(tableModel);
        add(new JScrollPane(courseTable), BorderLayout.CENTER);

    // Add Course Action
        addCourseButton.addActionListener(e -> addCourse());
        viewCoursesButton.addActionListener(e -> viewAllCourses());
        updateCourseButton.addActionListener(e -> updateCourse());
        deleteCourseButton.addActionListener(e -> deleteCourse());
        searchByInstructorButton.addActionListener(e -> searchByInstructor());
    }

//    add cources 

    private void addCourse() {
        JTextField courseNameField = new JTextField();
        JTextField feeField = new JTextField();
        JTextField instructorField = new JTextField();

        Object[] message = {
            "Course Name:", courseNameField,
            "Fee:", feeField,
            "Instructor:", instructorField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Course", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String courseName = courseNameField.getText();
                double fee = Double.parseDouble(feeField.getText());
                String instructor = instructorField.getText();
                courseList.add(new Course(courseName, fee, instructor));
                JOptionPane.showMessageDialog(this, "Course added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for fee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

// view cources 

    private void viewAllCourses() {
        tableModel.setRowCount(0); // Clear existing rows
        for (Course course : courseList) {
            tableModel.addRow(new Object[]{course.getId(), course.getCourseName(), course.getFee(), course.getInstructor()});
        }
    }

//  update cource 

    private void updateCourse() {
        String idInput = JOptionPane.showInputDialog(this, "Enter Course ID to Update:");
        if (idInput != null) {
            try {
                int id = Integer.parseInt(idInput);
                Course course = courseList.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
                if (course != null) {
                    JTextField courseNameField = new JTextField(course.getCourseName());
                    JTextField feeField = new JTextField(String.valueOf(course.getFee()));
                    JTextField instructorField = new JTextField(course.getInstructor());

                    Object[] message = {
                        "Course Name:", courseNameField,
                        "Fee:", feeField,
                        "Instructor:", instructorField
                    };

                    int option = JOptionPane.showConfirmDialog(this, message, "Update Course", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        course.setCourseName(courseNameField.getText());
                        course.setFee(Double.parseDouble(feeField.getText()));
                        course.setInstructor(instructorField.getText());
                        JOptionPane.showMessageDialog(this, "Course updated successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteCourse() {
        String idInput = JOptionPane.showInputDialog(this, "Enter Course ID to Delete:");
        if (idInput != null) {
            try {
                int id = Integer.parseInt(idInput);
                courseList.removeIf(course -> course.getId() == id);
                JOptionPane.showMessageDialog(this, "Course deleted successfully!");
                viewAllCourses();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchByInstructor() {
        String instructor = JOptionPane.showInputDialog(this, "Enter Instructor Name:");
        if (instructor != null) {
            tableModel.setRowCount(0); // Clear existing rows
            for (Course course : courseList) {
                if (course.getInstructor().equalsIgnoreCase(instructor)) {
                    tableModel.addRow(new Object[]{course.getId(), course.getCourseName(), course.getFee(), course.getInstructor()});
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CourseManagementApp app = new CourseManagementApp();
            app.setVisible(true);
        });
    }
}