package mpi;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class ConversationScope implements Scope{

    private ConversationHolder holder;
    
    public ConversationScope(ConversationHolder conversation) {
        this.holder = conversation;
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {

        Conversation conversation = holder.get();
        
        if(conversation == null){
            conversation = new Conversation(holder);
        }
        
        Object bean = conversation.resolveBean(name);
        if(bean == null){
            bean = objectFactory.getObject();
            conversation.registerBean(name, bean);
        }
        return bean;
    }

    @Override
    public Object remove(String name) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }

}
