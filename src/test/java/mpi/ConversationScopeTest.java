package mpi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mpi.ConversationScopeTest.ConversationBean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.lambdaj.Lambda;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestContext.class, Conversation.class, ConversationBean.class})
public class ConversationScopeTest {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private InitialStep step1;
    @Autowired
    private MiddleStep step2;
    @Autowired
    private FinalStep step3;

    @Autowired
    private Conversation c1;
    @Autowired
    private Conversation c2;
    
    @Autowired
    private ConversationBean bean;
    
    @Autowired
    private ConversationHolder holder;
    
    @Test
    public void shouldMantianTwoConversationScopesSimultanously() throws Exception {

        c1.start();                         // <------- c1
        step1.executeStep();
        step2.executeStep();
        
        c2.start();                         // <------- c2
        step1.executeStep();
        
        activeConversationIs(c1);           // <------- c1
        step3.executeStep();

        activeConversationIs(c2);           // <------- c2
        step3.executeStep();
        
        activeConversationIs(c1);           // <------- c1
//        assertThat(bean.printStatus())
//            .isEqualTo("Initial Step->Middle Step->Final Step");
        System.out.println("c1: " + bean.printStatus());
        
        
        activeConversationIs(c2);           // <------- c2           
//        assertThat(bean.printStatus())
//            .isEqualTo("Initial Step->Final Step");
        System.out.println("c2: " + bean.printStatus());

        activeConversationIs(c1);
        c1.end();
        activeConversationIs(c1);
//        assertThat(bean.printStatus()).isEqualTo("");
        System.out.println("c1: " + bean.printStatus());
        
    }

    // --
    
    private void activeConversationIs(Conversation conversation) {
        request.setAttribute("cid", conversation.getId());
    }
    
    @Component
    @Scope(value="conversation", proxyMode=ScopedProxyMode.TARGET_CLASS)
    public static class ConversationBean{
        
        private List<String> status = new ArrayList<String>();
        
        public void updateStatusWith(String msg){
            status.add(msg);
        }
        
        public String printStatus(){
            return Lambda.join(status, "->");
        }
    }
    
    @Component
    public static class InitialStep{
        
        @Autowired
        private ConversationBean session;
        
        public void executeStep(){
            session.updateStatusWith("Initial Step");
        }
    }
    
    @Component
    public static class MiddleStep{
        
        @Autowired
        private ConversationBean session;
        
        public void executeStep(){
            session.updateStatusWith("Middle Step");
        }
    }
    
    @Component
    public static class FinalStep{

        @Autowired
        private ConversationBean session;
        
        public void executeStep(){
            session.updateStatusWith("Final Step");
        }
    }
}
