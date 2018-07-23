package servlet;

import dao.BlockChain;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/nodes/register")
public class Nodes extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        // 读取客户端传递过来的数据并转换成JSON格式
        BufferedReader reader = req.getReader();
        String input = null;
        StringBuffer requestBody = new StringBuffer();
        while ((input = reader.readLine()) != null) {
            requestBody.append(input);
        }
        JSONObject jsonValues = new JSONObject(requestBody.toString());

        // 检查所需要的字段是否位于POST的data中
        String[] required = { "nodes" };
        for (String string : required) {
            if (!jsonValues.has(string)) {
                // 如果没有需要的字段就返回错误信息
                resp.sendError(400, "Missing values");
            }
        }

        // 新建交易信息
        BlockChain blockChain = BlockChain.getInstance();
        blockChain.registerNode(jsonValues.getString("nodes"));

        // 返回json格式的数据给客户端
        resp.setContentType("application/json");
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("total Nodes", blockChain.getNodes());
        response.put("message", "new Nodes have been added");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(new JSONObject(response));
        printWriter.close();
    }
}
