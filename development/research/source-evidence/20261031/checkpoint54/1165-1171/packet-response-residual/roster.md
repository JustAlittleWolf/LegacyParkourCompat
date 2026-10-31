# Frozen handler roster — ClientPacketListener direct player response audit

Before examining any prior source reports or comparing method bodies, I froze the roster as every `handle*` method declared in `ClientPacketListener.java` in each exact Mojmap tree. This includes all direct and potential client-level delegated packet routes; each handler will be screened for local-player targeting or mutation of position, velocity, box, pose, collision/ground, or movement predicates. The case-sensitive names/initial lines are captured in `clientpacket-handler-roster.txt`; it contains 89 handlers for 1.16.5 and all declarations for 1.17 / 1.17.1. Its `.sha256` sidecar binds the frozen list.

Exact pair boundaries are 1.16.5→1.17 and 1.17→1.17.1. After the blind handler roster is frozen, inspect every handler's dispatch/write consequences, follow delegated direct player mutations and client-level callbacks, compare changed relevant members on both boundaries, then cross-check earlier bounded reports. Disposition classes: eligible changed player-state response, paired no-difference, excluded server/world-state or nonplayer-only route, and open/unpaired exact handler/member.

No product source/history, wiki, backlog/research, or prior source report was read before freezing this roster. Only exact Mojmap source Java and the allowed source-discovery procedure were used to create the roster.
