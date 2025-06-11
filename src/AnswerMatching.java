import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AnswerMatching {
    public static void main(String[] args) {
        String userName="root";
        String url="jdbc:mysql://localhost:3306/studentdb";
        ArrayList<String> answerList=new ArrayList<>();
        ArrayList<Integer> markList=new ArrayList<>();
        int passMark=3;
        int totalMark=0;
        String status="Fail";

        try{
            Connection con= DriverManager.getConnection(url,userName,"");
            if(con!=null){
                System.out.println("connect");

                String sql="select CorrectAnswer,MarkQuestion from question where examID=?";

                PreparedStatement pstmt=con.prepareStatement(sql);

                pstmt.setString(1,"IPB3");

                ResultSet rs=pstmt.executeQuery();

                while(rs.next()){
                    answerList.add(rs.getString("CorrectAnswer"));
                    markList.add(rs.getInt("MarkQuestion"));
                }
                Scanner scanner=new Scanner(System.in);
                ArrayList<String> studentAnswerList=new ArrayList<>();

                int size=answerList.size();

                System.out.println("Enter Student Name : ");
                String Stu_Name=scanner.nextLine();

                for(int i=0;i<size;i++){
                    System.out.println("Answer for Question "+(i+1));
                    studentAnswerList.add(scanner.nextLine());
                }
                for(int i=0;i<answerList.size();i++){
                    if(answerList.get(i).equals(studentAnswerList.get(i))){
                        totalMark+=markList.get(i);
                    }
                }
                if(totalMark>=passMark){
                    status="Pass";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try{
            Connection con= DriverManager.getConnection(url,userName,"");
            if(con!=null){
                System.out.println("connect");

                String sql="insert into examresult (ExamID,EnrollmentID,TotalMarks,ResultStatus) values (?,?,?,?)";

                PreparedStatement pstmt=con.prepareStatement(sql);

                pstmt.setString(1,"IPB3");
                pstmt.setString(2,"11251-1");
                pstmt.setInt(3,totalMark);
                pstmt.setString(4,status);

                pstmt.execute();

                pstmt.close();
                System.out.println("Save all data...");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
