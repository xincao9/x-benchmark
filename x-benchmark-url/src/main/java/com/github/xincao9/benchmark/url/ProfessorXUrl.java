package com.github.xincao9.benchmark.url;

import com.github.xincao9.benchmark.core.ProfessorXCore;
import com.github.xincao9.benchmark.url.constent.SourceType;
import com.github.xincao9.benchmark.url.model.Configure;
import com.github.xincao9.benchmark.url.model.Login;
import com.github.xincao9.benchmark.url.model.Professor;
import com.github.xincao9.benchmark.url.model.Request;
import com.github.xincao9.benchmark.url.model.Source;
import com.github.xincao9.benchmark.url.util.HttpClientUtils;
import com.github.xincao9.benchmark.url.util.ShutdownHook;
import com.github.xincao9.benchmark.core.util.FileSource;
import com.github.xincao9.benchmark.core.util.Logger;
import com.github.xincao9.benchmark.core.util.SequenceSource;
import com.github.xincao9.benchmark.core.web.APIClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author 510655387@qq.com
 */
public class ProfessorXUrl {

    public static void main(String... args) throws IOException, URISyntaxException, InterruptedException {
        String conf;
        File file;
        List<String> cmd = new ArrayList<String>();
        if (args != null && args.length != 0) {
            if (args.length % 2 != 0) {
                help();
                return;
            } else {
                Map<String, String> params = new HashMap<String, String>();
                for (int i = 0; i < args.length; i += 2) {
                    params.put(args[i], args[i + 1]);
                    cmd.add(args[i]);
                    cmd.add(args[i + 1]);
                }
                if (params.containsKey("-conf")) {
                    conf = params.get("-conf");
                    file = new File(conf);
                    if (!file.exists()) {
                        help();
                        return;
                    }
                } else {
                    help();
                    return;
                }
            }
        } else {
            help();
            return;
        }
        cmd.add("-m");
        cmd.add("FlowMethod");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Configure configure = objectMapper.readValue(sb.toString(), Configure.class);
        Logger.info(objectMapper.writeValueAsString(configure));
        Configure.setConfigure(configure);
        Login login = configure.getLogin();
        if (login != null) {
            Logger.info(HttpClientUtils.getInstance().curl(login.getMethod(), login.getUrl(), null, login.getData()));
            Logger.info("login 模块启动");
            Thread.sleep(2000);
        }
        Professor professor = configure.getProfessor();
        APIClient apic = null;
        if (professor != null) {
            apic = new APIClient(professor.getHost(), professor.getPort(), professor.getAccount(), professor.getPasswd(), professor.getTopic(), professor.getTitle());
            Logger.info("professor 模块启动");
        }
        List<Request> flow = configure.getFlow();
        if (flow == null) {
            Logger.info("flow 模块是必须设置的");
            return;
        }
        Source source = configure.getSource();
        switch (SourceType.fromName(source.getType())) {
            case FILE: {
                if (apic != null) {
                    ProfessorXCore.bootstrap(new FileSource(source.getFilename(), source.getDiv(), source.getIndexs(), source.getSize()), apic, cmd.toArray(new String[0]));
                } else {
                    ProfessorXCore.bootstrap(new FileSource(source.getFilename(), source.getDiv(), source.getIndexs(), source.getSize()), cmd.toArray(new String[0]));
                }
                break;
            }
            case SEQUENCE: {
                if (apic != null) {
                    ProfessorXCore.bootstrap(new SequenceSource(source.getSize()), apic, cmd.toArray(new String[0]));
                } else {
                    ProfessorXCore.bootstrap(new SequenceSource(source.getSize()), cmd.toArray(new String[0]));
                }
                break;
            }
            default: {
                Logger.info("source 模块是必须设置的");
            }
        }
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }

    public static void help() {
        ProfessorXCore.help();
        Logger.info("请指定配置文件 : -conf professor_x_url.json");
    }
}
