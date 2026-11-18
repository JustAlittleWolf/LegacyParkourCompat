# Early client push reciprocal audit

Status: COMPLETED

## Scope and immutable source identities

Question: on the established 1.9 client path where an ordinary `LivingEntity` caller selects the local player as the pushed receiver, which entities receive velocity writes in 1.9 and 26.2, what client callers qualify, and whether a method-wide cancellation is source-compatible with player-only historical movement.

- 1.9 Feather `feather-gen2-1.9+build.2`; provenance `.decompile-provenance.json` SHA-256 `8DAACB7AF8F210E716EE4B34C43B8A5D6F6B4B19831D2FD725CBE16ECF330A1A`. Compared exact files: `LivingEntity.java` `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`; `EntityFilter.java` `f45827ffebad59c151aa5efa834824ba4d63bf60a645d14909df5d41f4b6e440`; `PlayerEntity.java` `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1`; `Entity.java` `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`.
- 26.2 unobfuscated, local provenance `decompiled_minecraft/26.2/unobfuscated.provenance.json` SHA-256 `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`; ready marker SHA-256 `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`; source inventory SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`. Relevant source hashes: `world/entity/LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `world/entity/Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `world/entity/player/Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `client/multiplayer/ClientLevel.java` `f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631`; `world/entity/EntitySelector.java` `0735f67b48018b7fc12737e06085cdf194a138edb75f5f95b83c0a9451be589b`.

## Source-supported facts

### 1.9 ordinary collision path

The already audited caller is `LivingEntity.tick`'s push section: it unconditionally calls `pushAwayCollidingEntities` (`1.9 .../LivingEntity.java:1700-1702`), which queries `EntityFilter.canBePushedBy(this)` and dispatches each candidate with `entity.push(this)` (`:1729-1741`). The client predicate rejects candidates except local `PlayerEntity` instances, after `NOT_SPECTATOR` and `isPushable`; collision-team rules still apply (`EntityFilter.java:55-92`). Thus in this ordinary client collision route the method receiver of `Entity.push` is local player; its argument is the entity whose living tick found it. It can be an ordinary non-player living entity, or a non-local/remote player if that player is the caller and follows this common path.

The local player override first excludes sleeping (`PlayerEntity.java:1880-1885`) then calls the base writer. In `Entity.push` (`:1057-1088`), a same-vehicle check, both no-clip checks, and horizontal threshold `absMax(dx,dz) >= 0.01F` guard both writes. After the exact normalization and `0.05F * (1.0F - receiver.pushSpeedReduction)` factor, receiver velocity is changed by `(-dx,0,-dz)` if the local player has no passengers; argument velocity is changed by `(+dx,0,+dz)` if that argument has no passengers. `addVelocity` adds to velocity and sets `velocityDirty` (`:1090+`). The second write is not restricted to players, and it is not restricted to local entities.

Consequently, yes: the same historical collision call that moves the local player's velocity also writes the reciprocal delta into a non-player caller (such as a mob), or into a remote-player client copy used as caller, if it passes the passenger gate. These other writes are source facts; their resulting non-player/remote trajectory is outside player-only movement scope. They must not be conflated with the local player's receiver-side write.

`hasSameVehicle` in 1.9 compares each entity's `getVehicle()` references (`Entity.java:2219-2221`), and the writer gates each side separately on `hasPassengers()`. The path is therefore unavailable for callers sharing the same vehicle and may write only one side when only one side has no passengers. Ordinary witness: an awake local player, ordinary alive non-player living caller, both pushable, default collision rules, intersecting boxes, no shared vehicle/noClip, horizontal separation at least `0.01F`, and neither passenger gate preventing the local write. No riding is needed.

### 26.2 comparison

Modern `LivingEntity` executes `pushEntities()` in its push section without a client-side guard (`.../LivingEntity.java:3167-3174`); the loop dispatches `entity.push(this)` (`:3232-3250, 3281-3283`). `ClientLevel.getPushableEntities` returns only the singleton local player when distinct from pusher, boxes intersect, and `EntitySelector.pushableBy(pusher)` passes (`.../ClientLevel.java:540-544`). That selector also admits only a local player candidate in client-side levels, after spectator/pushability and team-rule checks (`.../EntitySelector.java:29-58`). The same receiver/argument roles apply in the ordinary local-player collision path.

Modern `Entity.push(Entity)` still performs reciprocal writes, but with separate conditions: receiver writes only if it is not a vehicle and is pushable; argument writes only if it is not a vehicle and is pushable (`.../Entity.java:1876-1905`). Its vector overload adds to delta movement only for finite components and sets `needsSync` (`:1907-1917`). Modern 1.9 comparison therefore does not justify cancelling all of `Entity.push` for this collision: it would erase the local player's own side along with reciprocal writes. A receiver-side-only historical replacement/cancellation could preserve player behavior, but it would need the collision-call context and local-player receiver identity; suppressing the argument-side branch alone is not a faithful recreation of 1.9's complete call. Whether that reciprocal suppression is desired is a scope decision because it intentionally changes remote/non-player copy state outside the stated parity target.

### Client, server, vehicle and caller boundaries

- The 1.9 difference under discussion is a logical-client local-player response. The common call also runs on servers, where other eligible entities can be selected; historical movement must not be enabled on logical servers.
- For ordinary client collision, the selected receiver is specifically local player. A non-local/remote player is not selected as the candidate in this predicate; it can be the *argument/caller* and receive the reciprocal write.
- Both endpoints can be blocked by the same-vehicle and no-clip checks. The local receiver write additionally requires it to have no passengers; the reciprocal endpoint independently requires the argument to have no passengers. Sleeping local player blocks the virtual push before entering base logic.
- To characterize ordinary collision without sweeping unrelated uses of `Entity.push(Entity)`, tie the change to the `LivingEntity` common tick collision dispatch (`pushAwayCollidingEntities` -> `LivingEntity.pushAway` -> candidate `.push(this)`), with client world and receiver identity local player. A method-wide injection on `Entity.push` could catch direct calls from other mechanics; the bounded ordinary input alone does not license changing those callers.

## Hypotheses separated from evidence

- Source fact: reciprocal caller velocity writes occur on this path in 1.9 and modern source; conditions differ.
- Inference: because only local-player movement is in emulation scope, the remote/non-player reciprocal copy is not a parity output that needs to be reproduced. This does not by itself authorize removing it if doing so would alter unrelated state dependencies.
- Implementation implication: a whole-call cancellation is incompatible with the local player's required historical receiver response. A narrow collision-context adaptation that retains receiver-side write and optionally suppresses only the argument-side write is technically distinguishable in source, but would no longer emulate the full historical method side effect. The product owner must choose whether out-of-scope remote/non-player writes are left native or are excluded; this report does not inspect product architecture or implement a hook.

## Boundaries and blockers

This completes only the assigned source question: exact 1.9 and 26.2 caller/candidate/writer behavior and write-recipient scope. It is not a full release-pair inventory, does not examine intermediate releases, does not prove runtime parity, and does not determine a product hook seam. No product source/history/diff/wiki/backlog, tests, builds, launch, generation or shared primary-source writes were accessed.
