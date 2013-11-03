package mpi;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ConversationHolder {

    private HttpServletRequest request;
    
    private Map<Object, Conversation> conversations = new HashMap<Object, Conversation>();

    public ConversationHolder(HttpServletRequest request) {
        this.request = request;
    }

    public void set(Conversation conversation){
        if(conversation != null){
            Object cid = conversation.getId();
            conversations.put(cid, conversation);
            request.setAttribute("cid", cid);
        } else {
            conversations.remove(get().getId());
            request.removeAttribute("cid");
        }
    }
    
    public Conversation get(){
        return conversations.get(request.getAttribute("cid"));
    }

    public void reset(){
        set(null);
    }
}
