# Independent source audit: dry local-player horizontal input

## Scope and verdict

Compared exact Java 1.14 to 1.14.1 Yarn decompilations for ordinary dry local-player horizontal input: keyboard movement keys to signed axes, keyboard-input slowdown, input storage, local-player field consumption, `tickNewAi`, the `0.98F` horizontal-speed damping and the `Vec3d` passed to `travel`. The stipulated profile is unmounted, standing/crouching, nonsprinting, with ordinary ground or air movement. Crawling/swimming poses, item-use slowdown, sprint logic, flight, jump/climb, travel-body math, and all other assigned exclusions are outside this audit.

**Verdict: no source difference found in the bounded path.** This comparison yields no independent change finding for this slice. Input assembly applies no diagonal normalization: the two axis values remain independent signed floats through `Input`; `LivingEntity.tick` damps each horizontal field with `0.98F` before constructing the travel vector. These statements describe the inspected source path; trajectory effects are not inferred beyond it.

## Exact source identity

Both source trees are local, read-only inputs under `decompiled_minecraft/<version>/yarn/`. Their `.decompile-provenance.json` SHA-256 values match the assignment:

- Java 1.14: `EA70A955A12F377CD3B9E8D5F346F4625B044D55DA726B6BC472405AE91F4341`
- Java 1.14.1: `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907`

The manifests identify Yarn 1.14+build.21 (mapping archive SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`) and Yarn 1.14.1+build.10 (archive SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`). Their exact client input hashes are respectively `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` and `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`.

I checked the actual source files used below against each provenance manifest's `sources` entry. For each version, files are listed in this order: `client/network/ClientPlayNetworkHandler.java`, `client/input/KeyboardInput.java`, `client/input/Input.java`, `client/network/ClientPlayerEntity.java`, `entity/LivingEntity.java`.

- 1.14 SHA-256: `4fdd53d82e601147747b01b021a15482135f624c809a2722bb6a449ce272791f`; `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0`; `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351`; `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be`; `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6`.
- 1.14.1 SHA-256: `13db0a2baa2a0c986c795a2990eab64f3d9a4dc121e360dbd04c2ea84310ddd7`; `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0`; `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351`; `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933`; `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`.

## Retraced path and paired evidence

- `net/minecraft/client/network/ClientPlayNetworkHandler.java:360,1010`: normal local-player installation and replacement assign `new KeyboardInput(this.client.options)` to the player. Both releases do this at the same statements.
- `net/minecraft/client/input/KeyboardInput.java:13-25`: `tick` samples forward/back/left/right `isPressed()` booleans; each opposed pair maps to `0.0F` when equal, otherwise positive `1` for forward/left or negative `-1` for back/right. Jump and sneak booleans are sampled. If `!bl2 && (this.sneaking || bl)`, sideways and forward are each multiplied by double literal `0.3` and cast to `float`. There is no diagonal normalization. The complete class is byte-identical in both releases (SHA-256 above).
- `net/minecraft/client/input/Input.java:5-20`: stores the two axes as `float`; `tick` is empty here; `getMovementInput` returns `new Vec2f(this.movementSideways, this.movementForward)` without normalizing. The complete class is byte-identical in both releases.
- `net/minecraft/client/network/ClientPlayerEntity.java:613-624`: in `tickMovement`, computes `bl4` from sneaking pose / swimming-pose exit, then refreshes input with `this.input.tick(bl4, this.isSpectator())`. The following item-use `0.2F` slowdown is outside the stated ordinary-input scope. The relevant setup/call order is unchanged.
- `net/minecraft/client/network/ClientPlayerEntity.java:589-600`: after `super.tickNewAi()`, the camera player copies `input.movementSideways` into `sidewaysSpeed`, `input.movementForward` into `forwardSpeed`, and `input.jumping` into `jumping`. Code and operation order match.
- `net/minecraft/entity/LivingEntity.java:2174-2177,2198-2204`: when voluntarily moving, invokes `tickNewAi`; afterward multiplies `sidewaysSpeed` and `forwardSpeed` separately by `0.98F`, invokes `initAi`, then calls `travel(new Vec3d(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed))`. This call path and order match. Any input-to-velocity transformation within `travel` is beyond the requested handoff boundary.

## Limits

This is an independent audit of the assigned bounded source slice, not a full-pair movement inventory or pair-freeze verdict. It does not establish runtime parity. No product sources, Git history, research/backlog/wiki material, other auditors' artifacts, network lookup, decompilation, tests, build, or runtime were consulted or used.
## Phase 2 independent review

**Verdict: QUALIFIED ACCEPT** of the in-scope dry-input claims in author report SHA-256 `6085815A0E80CEBF3A873A10DA6FC7FB49A3FA30A73B115EDAFDB79EF930D964` (the 7,204-byte `author/report.md`). The report's paired claims that opposed keyboard axes map to signed `-1/0/1` values, the same sneak/pose gate scales both horizontal axes by `0.3`, `tickNewAi` copies those floats into local-player `sidewaysSpeed`/`forwardSpeed`, and inherited living-entity tick applies `0.98F` before constructing the `travel` vector agree with the independently retraced evidence above. No contrary in-scope claim or numeric/order discrepancy was found.

Qualification: the author's coverage explicitly bounds movement input as a supplied tick input and does not list `ClientPlayNetworkHandler` installation call sites. That leaves keyboard-input installation undocumented in that report. The frozen blind audit independently checked 1.14 and 1.14.1 `ClientPlayNetworkHandler.java:360,1010`, where the normal and replacement local player receive `new KeyboardInput(this.client.options)` identically. The gap does not change the accepted bounded claim, but the report alone does not evidence that upstream link. Item-use `0.2F` slowdown and other adjacent branches discussed by the author are outside this assignment's ordinary-input scope and are not accepted here.