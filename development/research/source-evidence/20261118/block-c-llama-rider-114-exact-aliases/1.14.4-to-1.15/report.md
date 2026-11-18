# Llama rider Y placement: 1.14.4 → 1.15

## Bounded result

For an ordinary adult vanilla Llama or TraderLlama carrying the sole direct local-player passenger, the source-confirmed Y placement is unchanged. No Y-position finding is supported by this pair. This is a bounded slice result, not full-pair tick coverage or a claim about intermediate patch introduction. Non-player/vehicle motion, other carriers and other movement operations were not examined.

## Exact source evidence

Both checked trees are exact-version local Mojmap sources; the 1.14.4 Mojmap tree was directly compared with 1.15 Mojmap (no later alias or generated substitute). The source manifests identify 1.14.4 with SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` and 1.15 with `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; these match the corresponding ready markers. The listed source-file digests below match each file's entry in its tree manifest.

Provenance: `decompiled_minecraft/1.14.4/mojmap.provenance.json` declares `versionId=1.14.4`, `namespace=mojmap`, exact run and `mappingOption=feather,mojmap`; its ready marker declares the exact version and client SHA `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. `decompiled_minecraft/1.15/mojmap.provenance.json` declares `versionId/requestedVersionId=1.15`, Mojmap, and its exact-run batch; it records client SHA `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`, version metadata SHA `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785`, and official client mappings SHA `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`. Its ready marker's source-manifest hash matches the actual manifest.

### Formula, types and dimensions

- `1.14.4` `world/entity/animal/horse/Llama.java:149–162`: virtual `positionRider(Entity)` gates on `hasPassenger(entity)`, computes `float f = cos(yBodyRot * (float)(PI / 180.0))`, `float g = sin(...)`, then calls `entity.setPos(this.x + 0.3F * g, this.y + this.getRideHeight() + entity.getRidingHeight(), this.z - 0.3F * f)`. `getRideHeight()` returns `this.getBbHeight() * 0.67`.
- `1.15` same file and member at `148–161`: same gate, float trigonometric operands and `0.3F` horizontal offsets; Y uses `this.getY() + this.getRideHeight() + entity.getRidingHeight()`. `getRideHeight()` remains `getBbHeight() * 0.67`.
- In both versions, `EntityType.java` gives LLAMA and TRADER_LLAMA dimensions `.sized(0.9F, 1.87F)`; PLAYER is `.sized(0.6F, 1.8F)`. The adult mount's `getBbHeight()` is float; `0.67` is a double literal, so Java promotes the float height to double before multiplication. `Player.getRidingHeight()` returns double `-0.35` in both (`Player.java:968–971` / `986–988`). The Y expression is left-associative, uses double entity Y, then double ride height, then double player riding height. No cast, literal, operand order, dimension, or gate changes in this Y calculation. TraderLlama extends Llama in both (`TraderLlama.java:21` / `22`) and does not override either placement or ride height.

### Local-player dispatch and callback boundary

`Player.rideTick()` on the client follows its `else` branch into `super.rideTick()` in both versions (`Player.java:455–465` / `459–469`). `Entity.rideTick()` zeroes delta movement, ticks the rider, then if `isPassenger()` invokes `getVehicle().positionRider(this)` (`Entity.java:1584–1590` / `1544–1550`). This is a virtual one-argument call, so a Llama passenger reaches the Llama override above; that method uses `entity.setPos` directly in both versions. LocalPlayer's `rideTick()` delegates to `super.rideTick()` (`LocalPlayer.java:819–820` / `825–826`).

1.15 additionally defines `Entity.positionRider(Entity, MoveCallback)` gated by `hasPassenger`, with the generic Y expression `getY() + getRideHeight() + entity.getRidingHeight()` (`Entity.java:1552–1560`). It is a distinct overload: Llama overrides only the one-argument method, so the callback overload is not the local-player `rideTick` dispatch. Its located caller is `Entity.teleportTo`: only when `level instanceof ServerLevel`, it calls `repositionDirectPassengers(Entity::forceMove)` (`Entity.java:2236–2245`; `repositionDirectPassengers` calls the two-argument overload at `2515–2518`). That is a logical-server teleport path, not historical local-player movement. Do not infer a Llama Y change from this callback addition.

## Exact file digests

SHA-256 of complete decompiled source files:

| Release | Source file | SHA-256 |
| --- | --- | --- |
| 1.14.4 | `mojmap/net/minecraft/world/entity/animal/horse/Llama.java` | `3e62e517a4bea2723cb4fd401d00a1de128e2dc1d5bd18dc5fd9e612449f296f` |
| 1.15 | same | `bbe98226c5afaddce8ca7458c721fbefff592cc75e02bc261d6b440ca6877f19` |
| 1.14.4 | `mojmap/net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.15 | same | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.14.4 | `mojmap/net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| 1.15 | same | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| 1.14.4 | `mojmap/net/minecraft/world/entity/EntityType.java` | `675b46b1b2680c222e9b160f2ff3610de40b4609a80124eed0a573f2502ea813` |
| 1.15 | same | `9a3ac5b01ebf9cb783e5b7073ae227ee2f58dd96437936f6aa21134d72a8d818` |
| 1.14.4 | `mojmap/net/minecraft/world/entity/animal/horse/TraderLlama.java` | `518a563fd44446b53c324be7c7cf8a0cce203901212720eef2454319a09ec9c9` |
| 1.15 | same | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` |
| 1.14.4 | `mojmap/net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `8f22326ddcf6e07aed445d17be2dcfa6fdf8e505eeea1c4bb06cfe216ad2e4d9` |
| 1.15 | same | `a967d0834d3c28080725a8b639d2c8c182ee6a7209a361d462fb0225a2836e6d` |
| 1.14.4 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.15 | same | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
