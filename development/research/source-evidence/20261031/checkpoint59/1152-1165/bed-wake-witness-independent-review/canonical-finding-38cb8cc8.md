# Independent binding review — canonical bed-wake finding

Reviewed canonical finding SHA-256: `38cb8cc8e7800527be49d4b3640970ad597239053536ea90f95870df9e1e803c`

## Verdict

**REVISE** before acceptance. The finding is bound semantically to the accepted concrete witness (report SHA-256 `d0fdc5ea63ecf1b2cb9f637255280c7873a4971501104b2096302175927e998e`) and accepted route review, and it preserves the paired behavior, corrected candidate coordinates, direct local-player position effect, and scope limits. One exact Mojmap source path is wrong.

## Checks

- The canonical finding file hashes to the requested revision.
- The 1.15.2 account matches the reviewed source: x-outer/z-inner scan starts at offset `(-1,-1)`, descends for support, adds `2.0E-7`, applies the two-block bound and accepts the first `noCollision` candidate at `(-0.5,64.0000002,-0.5)`.
- The 1.16.5 account matches the accepted witness: the non-bunk path orders the side candidate by bed/player yaw; the shape/supplier floor helper gives zero over full stone; dangerous-block and candidate collision gates pass. The corrected west/east centers are `(-0.5,64,0.5)` and `(1.5,64,0.5)`. Both differ horizontally from the old center and have Y `64.0` versus `64.0000002`.
- Attribution remains to the paired bed candidate algorithms and surrounding gates, rather than the helper alone. It says bed entry is out of scope and leaves the intermediate release boundary unchecked. The accepted route review confirms the action-2 bed-wake path reaches `Player.stopSleepInBed(false,false)` and the inherited local `LivingEntity.stopSleeping` position write; the finding's direct response statement is consistent with that route.
- **Required correction:** the finding cites `net/minecraft/world/entity/DismountHelper.java`, but the exact 1.16.5 Mojmap source file is `net/minecraft/world/entity/vehicle/DismountHelper.java`. Correct that path. Its member and line range `(82–98)` otherwise match the source.

No product, wiki, backlog, build, test, runtime, or decompile action was performed.
