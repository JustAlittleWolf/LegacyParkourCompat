# Independent source audit: Llama rider Y placement, 1.14.4 → 1.15

## Verdict

**Accepted for the bounded claim:** in the ordinary adult vanilla Llama / TraderLlama case where the local Player is the sole direct passenger, the audited source supports no change to the player's direct Y placement arithmetic between 1.14.4 and 1.15. This is a narrow source result, not full-pair movement coverage, an intermediate patch boundary, or a claim about other carriers or vehicle physics.

**Callback-overload precision:** the 1.15 two-argument `Entity.positionRider(Entity, MoveCallback)` is reached from `Entity.repositionDirectPassengers`, which is called by `Entity.teleportTo` only inside the `level instanceof ServerLevel` branch. It is also called by the base one-argument `Entity.positionRider(Entity)` through `this.positionRider(entity, Entity::setPos)`. The latter forwarding route does not change the local Llama rider path: `Entity.rideTick()` makes a virtual one-argument call on the vehicle, and Llama overrides that one-argument method. Thus for a local Player riding a Llama, the callback overload is not the ride-tick placement path; the teleport caller is server-only and outside logical-client movement. This audit makes no claim about other vehicles that inherit the base one-argument method.

## Immutable input and exact source identity

Frozen input: `c-llama-1144-115-position-intermediate/report.md`, SHA-256 `b7ae0b885b69ac44b901229a3a767ceda6a22b54628f8d2126399c54acd4f17b`.

Both local source trees identify the requested exact releases and Mojmap namespace. For 1.14.4, `decompiled_minecraft/1.14.4/mojmap.provenance.json` declares `versionId=1.14.4`, `namespace=mojmap`, `mappingOption=feather,mojmap`; `mojmap.ready.json` declares the same exact version and client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. Its actual `mojmap.sources.sha256` file hash matches the ready marker: `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`.

For 1.15, `decompiled_minecraft/1.15/mojmap.provenance.json` declares `versionId=requestedVersionId=1.15`, `namespace=mojmap`, and `mappingOption=mojmap`; it records client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`, version metadata SHA-256 `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785`, and official client mappings SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`. The actual `mojmap.sources.sha256` hash matches both provenance and ready marker: `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`.

For every source file listed below, I recomputed SHA-256 from the complete file and confirmed it equals both the frozen report value and that release's source-manifest entry.

| Mojmap source file | 1.14.4 SHA-256 | 1.15 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/animal/horse/Llama.java` | `3e62e517a4bea2723cb4fd401d00a1de128e2dc1d5bd18dc5fd9e612449f296f` | `bbe98226c5afaddce8ca7458c721fbefff592cc75e02bc261d6b440ca6877f19` |
| `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/EntityType.java` | `675b46b1b2680c222e9b160f2ff3610de40b4609a80124eed0a573f2502ea813` | `9a3ac5b01ebf9cb783e5b7073ae227ee2f58dd96437936f6aa21134d72a8d818` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `518a563fd44446b53c324be7c7cf8a0cce203901212720eef2454319a09ec9c9` | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` |
| `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `8f22326ddcf6e07aed445d17be2dcfa6fdf8e505eeea1c4bb06cfe216ad2e4d9` | `a967d0834d3c28080725a8b639d2c8c182ee6a7209a361d462fb0225a2836e6d` |

## Re-trace and comparison

- `Llama.positionRider(Entity)` is gated by `hasPassenger(entity)` in both releases (`Llama.java:150–156` in 1.14.4; `149–155` in 1.15). Its Y argument is `this.y + this.getRideHeight() + entity.getRidingHeight()` in 1.14.4 and `this.getY() + this.getRideHeight() + entity.getRidingHeight()` in 1.15. `Entity.getY()` in 1.15 returns `this.y` (`Entity.java:2700–2702`), so the base-Y operand is identical.
- `Llama.getRideHeight()` returns `this.getBbHeight() * 0.67` in both (`Llama.java:159–162` in 1.14.4; `158–161` in 1.15). `getBbHeight()` is float and `0.67` is a double literal, so multiplication promotes the float operand to double in both. The expression is left-associative; there is no changed cast, literal, order, gate or intermediate narrowing in Y.
- `Player.getRidingHeight()` returns double `-0.35` in both (`Player.java:968–971` in 1.14.4; `985–988` in 1.15). Llama passes the final sum directly to `entity.setPos` in both versions.
- `EntityType.java` registers LLAMA and TRADER_LLAMA with dimensions `.sized(0.9F, 1.87F)` and PLAYER with `.sized(0.6F, 1.8F)` in both releases (`EntityType.java:206, 283, 340` in 1.14.4; `208, 285, 342` in 1.15). These are the ordinary adult dimensions; the conclusion is conditioned on an adult Llama.
- `TraderLlama` extends `Llama` in both (`TraderLlama.java:21` / `22`) and declares no `positionRider` or `getRideHeight` override. Its ordinary placement therefore uses Llama's implementation.
- Local dispatch matches: `LocalPlayer.rideTick()` calls `super.rideTick()` (`LocalPlayer.java:819–820` / `825–826`); `Player.rideTick()` takes the non-server-sneak-stop branch for this client rider and calls `super.rideTick()` (`Player.java:455–465` / `459–469`); `Entity.rideTick()` calls `this.getVehicle().positionRider(this)` when `isPassenger()` (`Entity.java:1584–1590` / `1544–1550`). This is virtual one-argument dispatch to Llama's override.
- In 1.15, base `Entity.positionRider(Entity)` forwards to the callback overload at `Entity.java:1552–1554`; the overload gates on `hasPassenger` and applies the generic `getY() + getRideHeight() + entity.getRidingHeight()` through the callback at `1556–1560`. `repositionDirectPassengers` calls the two-argument overload (`2515–2518`), and the checked caller is the `ServerLevel`-guarded `teleportTo` branch (`2236–2245`). Llama's override means the base one-argument forwarding implementation is not selected for this local Llama rider tick.

### Boundary

This verdict addresses only source identity, direct local-player dispatch and Y placement for an adult vanilla Llama / TraderLlama between these two exact releases. It does not audit another carrier, vehicle motion, non-player movement, the full tick graph, or a runtime trajectory.