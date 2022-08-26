package pl.szczeliniak.ftpoverhttpserver.core.shared;

public interface Command<REQ, RES> {

    RES execute(final REQ request);

}
