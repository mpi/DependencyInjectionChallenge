package mpi;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mpi.ConversationScopeTest.FinalStep;
import mpi.ConversationScopeTest.InitialStep;
import mpi.ConversationScopeTest.MiddleStep;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;


public class TestContext{

    private HttpServletRequest request = new MockHttpServletRequest();

    @Bean
    public ConversationHolder holder(){
        return new ConversationHolder(request);
    }
    
    @Bean
    public HttpServletRequest request(){
        return request;
    }
    
    @Bean
    public InitialStep initialStep(){
        return new InitialStep();
    }

    @Bean
    public MiddleStep middleStep(){
        return new MiddleStep();
    }
    
    @Bean
    public FinalStep finalStep(){
        return new FinalStep();
    }
    
    @Bean
    public CustomScopeConfigurer customScopes(ConversationHolder holder){
        CustomScopeConfigurer custom = new CustomScopeConfigurer();
        Map<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("conversation", new ConversationScope(holder));
        custom.setScopes(scopes);
        return custom;
    }
}
