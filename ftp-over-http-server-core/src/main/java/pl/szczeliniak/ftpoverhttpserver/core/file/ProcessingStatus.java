package pl.szczeliniak.ftpoverhttpserver.core.file;

public enum ProcessingStatus {

    PROCESSING(false, false),
    UPDATING(false, false),
    READY(true, true),
    DELETING(false, false),
    DELETED(false, false);

    private final boolean isModifiable;
    private final boolean isDownloadable;

    ProcessingStatus(final boolean isModifiable, final boolean isDownloadable) {
        this.isModifiable = isModifiable;
        this.isDownloadable = isDownloadable;
    }

    public boolean isModifiable() {
        return isModifiable;
    }

    public boolean isDownloadable() {
        return isDownloadable;
    }
}
