package legacyparkourcompat.minecraft;

import com.google.gson.annotations.SerializedName;

import java.util.List;

final class MojangMeta {
    private MojangMeta() {
    }

    static final class VersionManifest {
        Latest latest;
        List<VersionRef> versions;
    }

    static final class Latest {
        String release;
        String snapshot;
    }

    static final class VersionRef {
        String id;
        String type;
        String url;
        String releaseTime;
    }

    static final class VersionJson {
        String id;
        Downloads downloads;
        List<Library> libraries;
        JavaVersionSpec javaVersion;
    }

    static final class JavaVersionSpec {
        int majorVersion;
    }

    static final class Downloads {
        Artifact client;
        Artifact server;
        @SerializedName("client_mappings")
        Artifact clientMappings;
        @SerializedName("server_mappings")
        Artifact serverMappings;
    }

    static final class Artifact {
        String sha1;
        String url;
        long size;
        String path;
    }

    static final class Library {
        String name;
        LibraryDownloads downloads;
        List<Rule> rules;
    }

    static final class LibraryDownloads {
        Artifact artifact;
    }

    static final class Rule {
        String action;
        Os os;
    }

    static final class Os {
        String name;
    }

    static final class YarnBuild {
        String maven;
        String version;
        int build;
        boolean stable;
    }
}
