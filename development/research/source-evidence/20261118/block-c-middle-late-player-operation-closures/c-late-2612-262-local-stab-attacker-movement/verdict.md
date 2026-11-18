# Independent audit: 26.1.2 → 26.2 local piercing `stabAttack` reach

## Frozen input

Audited report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-late-2612-262-local-stab-attacker-movement/report.md`

SHA-256 at audit start: `CD13F04751DF3F2294CB5804716EB598856D459E7D40588F705CD9E3ADA21D17`.

## Verdict

**Accepted for the bounded C action: no ordinary local-player reach to `Player.stabAttack`; no local attacker movement or sprint delta from this operation. No movement finding/action.** The compared method bodies do differ, but ordinary input hands the piercing action to the server, and kinetic-weapon use ticks skip `KineticWeapon.damageEntities` on the logical client. This verdict does not cover damage outcomes, targets, other actors, crafted/custom states, or runtime parity.

The conditional body delta is real. `Player.stabAttack` first rejects `cannotAttack(target)` and can return after `deflectProjectile(target)`. In 26.1.2 its knockback call combines `0.4F + getKnockback`; inherited `LivingEntity.causeExtraKnockback` writes the attacker's horizontal velocity only when the amount is positive **and** the target is a `LivingEntity`, and it does not clear sprinting. In 26.2 it calls fixed `0.4F` and the attribute-derived amount separately. The `Player.causeExtraKnockback` override gates target effects and its own horizontal-velocity attenuation plus `setSprinting(false)` on `knockbackAmount > 0.0F`. The fixed call therefore reaches those writes only if `stabAttack` passes its early return gates. `getKnockback` reads `ATTACK_KNOCKBACK` and divides by two outside `ServerLevel`; the vanilla attribute base is zero. These are hypothetical local effects because the ordinary local path below does not invoke the method.

## Independent trace and references

- **Input dispatch:** `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/client/Minecraft.java:1646-1685` and `26.2/.../Minecraft.java:1626-1667`, `startAttack`, branch on held `PIERCING_WEAPON`; it calls `gameMode.piercingAttack`, swings, and returns. The ordinary spear component is installed with `dealsKnockback=true` in `26.1.2/.../world/item/Item.java:516-524` and `26.2/.../Item.java:517-525`.
- **Client owner:** `26.1.2/.../client/multiplayer/MultiPlayerGameMode.java:515-521` and `26.2/.../MultiPlayerGameMode.java:520-526`. `piercingAttack` sends `Action.STAB` then calls `onAttack`, `postPiercingAttack`, and sound handling; it does not call `PiercingWeapon.attack` or `Player.stabAttack` locally.
- **Packet receiver:** `26.1.2/.../server/network/ServerGamePacketListenerImpl.java:1273-1287` and `26.2/.../ServerGamePacketListenerImpl.java:1280-1295` resolve the server player's main-hand component and call `piercingWeapon.attack(this.player, MAINHAND)`. `26.1.2/.../world/item/component/PiercingWeapon.java:69-89` and 26.2 at the same lines contain the call from `attack` to `attacker.stabAttack`.
- **Kinetic use tick:** `26.1.2/.../world/item/ItemStack.java:1092-1104` and 26.2 at the same lines call `kineticWeapon.damageEntities` only when `kineticWeapon != null && !level.isClientSide()`; the client takes `Item.onUseTick` instead. The only `KineticWeapon.damageEntities` caller is `ItemStack.onUseTick`; the `stabAttack` call in that method is at `26.1.2/.../KineticWeapon.java:98-132` and 26.2 at the same lines. Full-pair source search finds no other vanilla `stabAttack` call sites in either tree.
- **Attacker/target writers:** `26.1.2/.../world/entity/player/Player.java:1193-1239`, `26.1.2/.../world/entity/LivingEntity.java:2688-2693`; `26.2/.../Player.java:1122-1157,1213-1260`, `26.2/.../LivingEntity.java:2745-2759`. The 26.2 player override performs target knockback/push before its separately gated self writes. The server-only `ServerPlayer` motion restoration in `Player.causeExtraKnockback` is not a local attacker movement path. `getKnockback` is `26.1.2/.../LivingEntity.java:1511-1515` and 26.2 at `1540-1545`; vanilla attribute base is zero at `26.1.2/.../Attributes.java:15` and `26.2/.../Attributes.java:18`.

## Canonical source identity verified

All hashes below are SHA-256 over the local canonical files under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Both releases identify `unobfuscated`; provenance says Vineflower `1.12.0` and Java `25.0.3+9-LTS`.

| Release | Provenance | Ready marker | Source manifest | Artifact manifest | Diagnostics |
|---|---|---|---|---|---|
| 26.1.2 | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02` |
| 26.2 | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3` |

The ready markers match their release and `unobfuscated` mapping; each marker's source-manifest, artifact-manifest, and diagnostics digests match the local files. The report's ten compared Java-file hashes were independently recomputed and each matched its source-manifest entry on both sides. The 26.2 ready marker does not declare a client-JAR SHA, so this audit does not claim one.
