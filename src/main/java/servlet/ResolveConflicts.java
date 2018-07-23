package servlet;

import dao.BlockChain;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/nodes/resolve")
public class ResolveConflicts extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        BlockChain blockChain = BlockChain.getInstance();

        // 返回json格式的数据给客户端
        resp.setContentType("application/json");
        Map<String, Object> response = new HashMap<String, Object>();


        //如果邻居节点链更长则替换
        if (blockChain.resolveConflicts()){

            response.put("message", " longer chain have been added");
        }else{
            response.put("message", "our chain is longest");
        }
        response.put("chain", blockChain.getChain());
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(new JSONObject(response));
        printWriter.close();
    }
}
