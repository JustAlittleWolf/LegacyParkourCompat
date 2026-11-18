# Independent source verdict: C yaw, 1.13 → 1.13.1

**Verdict: ACCEPT, bounded claim only.** No yaw-only difference appears in the audited endpoint operation path. This is not full-pair acceptance, implementation approval, or runtime parity.

## Reviewed immutable claim

- Draft commit: `2c155a088b9b19076dd1a658913c3bccdafa6e85`
- Draft path: `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-yaw-precision-113-1131-source/report.md`
- Draft blob: `e65f3cd9aa25d6d2f36f7cc384673ffa35f523c0`
- Raw Git payload SHA-256: `d95c35c1bf746ca8d40b7cff6c8995f8aef57dce3982b3bfc87c91bdfc301dab` (7,100 bytes)

## Exact source identity

Both sides use the `ornithe-feather` namespace from preparation run `prep-1.13-1.13.1-feather-2026-10-09-r1`. Pinned mapping coordinates are `net.ornithemc:feather-gen2:1.13+build.2` (SHA-256 `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`) and `net.ornithemc:feather-gen2:1.13.1+build.2` (SHA-256 `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d`). Provenance SHA-256: 1.13 `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`; 1.13.1 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`. Source manifest SHA-256: 1.13 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; 1.13.1 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`. The audited source-file hashes match the corresponding manifests.

## Audited operation and evidence

`MouseHandler.turnPlayer` sends mouse deltas to the player only while grabbed and fullscreen; the other branch clears accumulated deltas. `Entity.updateLocalPlayerCamera` computes yaw delta as `double cursorDeltaX * 0.15`, then independently stores `(float)(yaw + e)` and `(float)(lastYaw + e)`. Yaw is neither clamped nor wrapped there. For the ordinary player branch, `PlayerEntity.moveRelative` delegates to `LivingEntity.moveRelative`; when dry and not fall-flying, the latter selects `getSpeed() * u` on ground or `speedInAir` airborne, then calls `Entity.updateVelocity`. That consumer applies its existing control-vector dead zone, converts stored float yaw for `MathHelper.sin/cos`, and rotates the controls without a yaw finite-value guard. `MathHelper.sin/cos` use the 65,536-entry table and integer cast plus `& 65535`; the report's finite-input overflow witness therefore follows the described source arithmetic.

The audited method bodies match across 1.13 and 1.13.1. Independently reproduced body SHA-256 values: `updateLocalPlayerCamera` `a08edd945453b3f76bc3de93b63fad3bcc1b4913603ba8f602f51fdadd37895a`; `updateVelocity` `ce5c24df63c34a398c66f03759c8cf9b403cc2d9f50b28baf204037d75e48f3f`; `LivingEntity.moveRelative` `78de2dc443a97a6d6d08a3e430b0a5dbcb967ad73a5dddd14d9f7169cec56e82`; `PlayerEntity.moveRelative` `e2bec35ade0d77ec03898370e7466fc6f7504eb9629e2e9590c85804742093ae`. The `MouseHandler.turnPlayer` bodies also match.

**Scope boundary:** This verdict accepts only the cited local stored-yaw arithmetic and its first ordinary dry ground/air relative-acceleration consumer for this exact endpoint pair. It says nothing about intermediate patch boundaries, other movement slices, complete pair coverage, implementation, or runtime behavior.
