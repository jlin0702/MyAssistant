package com.example.springbootfullstack.controller;

import com.example.springbootfullstack.UserRepository;
import com.example.springbootfullstack.Entity.User;
import com.example.springbootfullstack.model.NavLink;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Controller
//@RequestMapping("/user")
//@RestController
public class UserController {
    @PostConstruct
    public void init() {
        System.out.println("UserController  init    hello world");
        nav = Arrays.asList(
                new NavLink("Home", "/"),
                new NavLink("Article", "/article"),
                new NavLink("Log", "/log"),
                new NavLink("Greeting", "/greeting"),
                new NavLink("Resume", "/pdf"),
                new NavLink("Ask ChatGPT", "/ask")
        );
    }

    private static Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @Value("${server.port}")
    private String port;
    @Value("${spring.datasource.url}")
    private String url;
    private List<NavLink> nav;
    @RequestMapping("/")
    public String index(Model model) {
        logger.trace("================ trace ================");
        logger.debug("================ debug ================");
        logger.info("================ info ================");
        logger.warn("================ warn ================");
        logger.error("================ error ================");
        model.addAttribute("nav", nav);
        model.addAttribute("name","Jacky");
        model.addAttribute("gender", "Male");
        model.addAttribute("hobby", "Playing Games");
        model.addAttribute("personality", "Lively");
        model.addAttribute("hometown", "New York");
        model.addAttribute("major", "Computer Science");
        try {
            URL url = new URL("https://zenquotes.io/api/today");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseStream);
            model.addAttribute("quote", root.get(0).path("q").asText());
            model.addAttribute("author", root.get(0).path("a").asText());
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return "index";
    }

    @Autowired // get bean userRepository
    private UserRepository userRepository;

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("nav", nav);
        model.addAttribute("user", new User());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("nav", nav);
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setAge(user.getAge());
        newUser.setGender(user.getGender());
        newUser.setEmail(user.getEmail());
        newUser.setCity(user.getCity());
        userRepository.save(newUser);
        return "result";
    }

    @GetMapping("/log")
    public String getMessage(Model model) {
        model.addAttribute("nav", nav);
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "log";
    }

    @GetMapping("/article")
    public String getArticle(Model model) {
        model.addAttribute("nav", nav);
        return "article";
    }

    @GetMapping("/ask")
    public String askChatgpt(Model model)
    {
        model.addAttribute("nav", nav);
        return "ask";
    }

    @PostMapping("/ask")
    public String Chatgpt(String question, Model model) {
        String token = System.getenv("OPENAI_TOKEN");

        System.out.println(question);
        OpenAiService service = new OpenAiService(token);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(question)
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);

        List<CompletionChoice> choicesList = service.createCompletion(completionRequest).getChoices();

        String answer = choicesList.get(0).getText();
        model.addAttribute("nav", nav);
        model.addAttribute("choices", answer);
        return "ChatGptAnswers";
    }
}
