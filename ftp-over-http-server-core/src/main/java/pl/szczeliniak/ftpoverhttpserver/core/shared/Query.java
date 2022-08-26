package pl.szczeliniak.ftpoverhttpserver.core.shared;

public interface Query<REQ, RES> {

    RES execute(final REQ request);

}
