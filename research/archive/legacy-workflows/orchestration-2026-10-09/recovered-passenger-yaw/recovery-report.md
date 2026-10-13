# Passenger-yaw recovery checkpoint

- Recovery checkout: `C:/Users/Wolfi/.codex/worktrees/movement-passenger-yaw-2026-10-08`
- Branch and pre-recovery HEAD: `feat/movement-passenger-yaw-2026-10-08` at `32c8b0a7e089e32d2b755a513241ac0e58d1d562`.
- Before recovery, the index held exactly six staged paths, all with no additional worktree delta: three Java modifications and three report deletions. The exported `staged.patch` is 16,146 bytes, SHA-256 `BADE4FABFA43F9EC1C9E0025EAEF6F9B33763C904941314EAE7DD61B9317FB67`; `unstaged.patch` is empty (SHA-256 `E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855`). `status-before-recovery.txt` records the complete porcelain-v2 index state. These files preserve all old staged intent before reconciliation.
- Staged deletion identities: implementation record blob `9962f5d6a4b5cbdf274b48cbf1da773dadd86d8d`; integration queue blob `651ebe42...` (full identity is retained in `staged.patch`); integration review blob `6a657aaa379cc4d21b05a1a6b8328315ae4f2285`.

## Evidence and disposition

The source finding is immutable snapshot `064fc24e5e3d8b4dab4f12c2000dcb13d37514ca` (file SHA-256 `3994115e3789a8280aa61228b2b092a007e57985cde72492f19731a8f150db95`), independently accepted at `4ff925e4e4186adb704d57d9b3471c559945436c`. It bounds the old behavior to the accepted 1.17.1-vs-1.18.2 transition and the player's yaw consumed by later movement.

The initial static review `fddb2634de8c406e62a3baabd391f5a414f0fa55` requested restoring all yaw fields and constraining the behavior to a direct remount. The subsequent independent correction review `7c40951938a925fbb6fae541a95b462006113c09` accepted code tip `32c8b0a7e089e32d2b755a513241ac0e58d1d562`, including correction `5c2cc08f6b8eaccb02f89a80de4823a33ac37503`. That accepted review verifies the successful direct-local-player-remount event gate, retains pre-rebuild indirect membership to allow nested-to-direct transition, and accepts only the movement-facing `yRot` write; it explicitly finds no need to restore interpolation/head-render yaw fields. Current main at recovery comparison is `52cbecd94c5046a05c16c7fc4d4f649ad464868c` and contains this accepted code.

The staged `ClientPacketListenerMixin` edit removes the accepted successful-remount gate and substitutes an indirect-membership check after passenger rebuilding. It reopens the nested-passenger case rejected by the review and is an unjustified regression. The staged `LocalPlayerMixin` move from `aiStep` HEAD to TAIL also reverses accepted TICK-01 ordering: the 600-tick stop must precede sprint eligibility and travel (`3c531180287b3cecff49e557083db73352b258f7`, integrated as `3699009`). Its `SprintTickBehavior` comment change follows that regression and is also rejected.

All three report deletions are unjustified: the implementation record, integration queue, and review are retained historical evidence and contain the accepted correction and its limits. The deletion intent remains preserved in `staged.patch`; the source reports are to be restored. F-006 is already implemented and independently accepted on current main. No additional code correction is supported by the accepted follow-up review, and runtime parity remains unverified.

## Recovery action

Commit this patch archive and report first as an immutable preservation checkpoint. Then restore the six staged paths to the accepted/current-main state, preserving all three historical reports, merge the then-current `main` into this task branch, and inspect same-file changes in `LocalPlayerMixin` for semantic overlap. Do not build, test, decompile, run clients/TAS/servers/Docker, or push.
