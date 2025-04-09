package biog4m3.echoforge;

public interface ISystem {
    public void init(GameContext context) throws Exception;
    public void tick(GameContext context) throws Exception;
    public void render(GameContext context) throws Exception;
    public void shutdown(GameContext context) throws Exception;
}
