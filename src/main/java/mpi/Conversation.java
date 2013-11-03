package mpi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class Conversation {

    private UUID id = UUID.randomUUID();

    private ConversationHolder holder;

    private Map<String, Object> beans = new HashMap<String, Object>();
    
    @Autowired
    public Conversation(ConversationHolder holder) {
        this.holder = holder;
    }

    public void start() {
        holder.set(this);
        System.err.println("Starting conversation with id=" + getId());
    }

    public Object getId() {
        return id;
    }

    public void end() {
        holder.reset();
    }

    public Object resolveBean(String name) {
        return beans.get(name);
    }

    public void registerBean(String name, Object bean) {
        beans.put(name, bean);
    }

}
