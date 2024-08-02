package vn.jobhunter.jobhunter.domain.response.resume;

import java.time.Instant;

public class ResUpdateResumeDTO {
    private Instant updatedAt;
    private String updateBy;

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
