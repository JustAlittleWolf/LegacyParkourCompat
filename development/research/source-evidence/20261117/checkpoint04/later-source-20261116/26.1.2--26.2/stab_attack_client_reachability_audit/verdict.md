# Independent audit — Player.stabAttack client reachability

Verdict: **ACCEPT** for the report's bounded conclusion that direct LocalPlayer execution of the two inspected `Player.stabAttack` weapon routes is not established. This does not establish absence of indirect server-authoritative movement responses, does not cover all movement writes, and does not freeze the 26.1.2→26.2 pair.

## Bound report and exact source identities

Audited report: `build/later-source-20261116/26.1.2--26.2/stab_attack_client_reachability/coverage.md`  
SHA-256: `1f56cb81564d4c0604fce15cec559f4ca58157de2c94585de2725c4e1e75e95c` (matches the assigned digest).

Both sources are the exact published `unobfuscated` namespace at `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`. I verified the actual sidecar forms and bytes: `26.1.2/unobfuscated.provenance.json` SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; `26.2/unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`. Both declare their expected version IDs and namespace. The version-root `unobfuscated.ready.json` and `unobfuscated.sources.sha256` files match report-stated identities. I checked all 20 cited Java-file hashes (10 per version) against current bytes and source-manifest rows; all match. No full tree rehash or artifact verification was performed.

## Paired caller-path review

- In both versions, the client input path reaches `Minecraft.startAttack()` for an attack input. After the spectator, item-enabled and attack eligibility gates, a held item with `PIERCING_WEAPON` calls `MultiPlayerGameMode.piercingAttack`. That method sends `ServerboundPlayerActionPacket.Action.STAB`, then performs local attack/post-attack callbacks, swing and sound; it does not call `PiercingWeapon.attack` or `Player.stabAttack`.
- In both versions, the server packet listener handles `STAB` using its `this.player`, rejects spectators and `cannotAttackWithItem`, then invokes `PiercingWeapon.attack` for the held component. That player is the server-side player. `PiercingWeapon.attack` calls `attacker.stabAttack`; the local client request is therefore not a direct LocalPlayer execution of the changed attacker damping.
- For held-use `KineticWeapon`, `ItemStack.onUseTick` invokes `damageEntities` only when the component is present and `!level.isClientSide()`. Its `stabAttack` call is inside that server-only scan. The client takes the ordinary item `onUseTick` branch.
- I independently searched both complete published Java trees for `stabAttack(`. In each tree the only invocations are `KineticWeapon.damageEntities` and `PiercingWeapon.attack`; other matches are the `LivingEntity` and `Player` declarations. This supports the report's bounded source call-site inventory and its direct-reachability limit.

Thus the report correctly leaves direct LocalPlayer reachability through these two weapon mechanisms unestablished. A server-side player may be changed and later synchronized to the client; that separate response path is expressly outside this report and this verdict. No claim is made that the accepted Player method-body difference has no movement consequence in all contexts.

No backlog/wiki/product/Git history was consulted; no canonical finding/backlog file was written; no source generation, build, test, or runtime check was performed.