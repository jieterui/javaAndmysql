import java.sql.*;
import java.util.Scanner;
public class Main {
    // JDBC 数据库连接信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_db"; // 数据库 URL
    private static final String DB_USERNAME = "root"; // 数据库用户名
    private static final String DB_PASSWORD = ""; // 数据库密码
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 提示用户输入用户名和密码
        System.out.println("请输入用户名：");
        String usernameInput = scanner.nextLine();

        System.out.println("请输入密码：");
        String passwordInput = scanner.nextLine();

        // 验证用户名和密码
        if (authenticate(usernameInput, passwordInput)) {
            System.out.println("登录成功！欢迎 " + usernameInput);
        } else {
            System.out.println("用户名或密码错误！");
        }

        scanner.close();
    }

    // 验证用户名和密码
    public static boolean authenticate(String username, String password) {
        // 创建数据库连接
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // SQL 查询语句，检查用户是否存在且密码匹配
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

            // 创建 PreparedStatement
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // 设置查询参数
                stmt.setString(1, username);
                stmt.setString(2, password);

                // 执行查询
                try (ResultSet rs = stmt.executeQuery()) {
                    // 如果有匹配的用户，说明验证成功
                    if (rs.next()) {
                        return true;
                    } else {
                        return false; // 用户名或密码不匹配
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("数据库连接或查询出错：" + e.getMessage());
            return false; // 出现异常时返回 false
        }
    }
}
