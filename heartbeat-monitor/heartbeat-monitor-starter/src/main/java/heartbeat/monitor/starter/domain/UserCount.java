package heartbeat.monitor.starter.domain;

/**
 * @apiNote 封装离线用户数，以及在线用户数
 */
public class UserCount {
    private Long inLineUsers;  // 在线用户数
    private Long outLineUsers; // 离线用户数
    private String flag; // 系统标识
    private String prepositionId; // 部委前置的唯一标识

    public Long getInLineUsers() {
        return inLineUsers;
    }

    public void setInLineUsers(Long inLineUsers) {
        this.inLineUsers = inLineUsers;
    }

    public Long getOutLineUsers() {
        return outLineUsers;
    }

    public void setOutLineUsers(Long outLineUsers) {
        this.outLineUsers = outLineUsers;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPrepositionId() {
        return prepositionId;
    }

    public void setPrepositionId(String prepositionId) {
        this.prepositionId = prepositionId;
    }
}
