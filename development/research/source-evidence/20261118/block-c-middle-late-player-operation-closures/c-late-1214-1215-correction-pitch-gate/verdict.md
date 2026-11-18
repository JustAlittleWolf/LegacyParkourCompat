# Independent source audit: 1.21.4–1.21.5 correction pitch gate

## Verdict

**Conditionally accepted for the bounded ordinary local movement-correction claim.** The reported helper change and rotated-velocity order are correct. The ordinary local server rejection/correction route supplies an absolute rotation with no `ROTATE_DELTA`, and normal local pitch is bounded before the packet is sent. I found no source-reachable ordinary local correction with both an out-of-range composed pitch and the velocity-rotation flag.

Narrow one general statement in the input report: “With no `ROTATE_DELTA` ... this clamp has no effect on correction movement state” is true for the traced ordinary local path, but not as an unconditional helper-level statement. `Entity.setXRot` applies `% 360.0F` before clamping. Thus, for arbitrary out-of-range endpoint data, the 1.21.4 helper result can normalize differently from the 1.21.5 pre-clamp (for example, a composed 270° pitch becomes -90° through the old setter and +90° through the new helper/setter). Such input is outside this audit’s ordinary-local-path boundary; it should not be used to claim a difference for a normal movement correction.

## Frozen input and source identity

Audited input, read-only:

- `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-late-1214-1215-correction-pitch-gate/report.md`
- SHA-256: `aadfb14b9e8d9bc08cce70ecee1f39732d0aa13b2dbc325ad5964526d318f53a`

Canonical local inputs: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.21.4,1.21.5}/mojmap/`. Both ready markers identify the exact version, Mojmap namespace, Vineflower, Java 25, and `ready`; both provenance records name the run `decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G`.

| Version | Ready-marker SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 | Provenance JSON SHA-256 |
| --- | --- | --- | --- | --- |
| 1.21.4 | `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9` |
| 1.21.5 | `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` | `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e` |

The inspected Java bytes matched their source-manifest entries:

| Relative source path | 1.21.4 SHA-256 | 1.21.5 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/PositionMoveRotation.java` | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` | `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `c5467cc282f361b5ab5540066385240f847d101c68afdef8655908f3fb3b2c8b` | `fdb8726f77618ebcfd4fe3973e1740d999c2fd3d220ca4a3ddba837f160e6d69` |
| `net/minecraft/world/entity/Entity.java` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` |
| `net/minecraft/client/player/LocalPlayer.java` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` |

## Source findings

- `PositionMoveRotation.calculateAbsolute`: 1.21.4 lines 35–57 adds the selected base pitch and correction pitch as `float` and returns it without a helper clamp (lines 40–43, 57). 1.21.5 lines 34–56 clamps that sum with `Mth.clamp(..., -90.0F, 90.0F)` at line 42. If `Relative.ROTATE_DELTA` is present, both versions rotate the original delta vector around X by `radians(oldPitch - composedPitch)` first, then around Y by `radians(oldYaw - composedYaw)`; the optional relative delta components are calculated afterward (1.21.4 lines 44–57; 1.21.5 lines 43–56). The mathematical helper-level change is confirmed.
- Server ordinary movement correction: `ServerGamePacketListenerImpl.handleMovePlayer` wraps incoming yaw/pitch with `Mth.wrapDegrees` in both releases (1.21.4 lines 902–903; 1.21.5 lines 970–971). Its normal correction branches call the five-argument `teleport` overload; for rejected movement this is 1.21.4 lines 1009–1011 and 1.21.5 lines 1084–1087. That overload constructs `PositionMoveRotation(..., Vec3.ZERO, yaw, pitch)` and delegates with `Collections.emptySet()` (1.21.4 lines 1067–1079; 1.21.5 lines 1145–1157). Therefore the ordinary correction has no relative pitch and no `ROTATE_DELTA` flag.
- Normal local pitch input: `LocalPlayer.sendPosition` builds its movement packets from `getXRot()` (1.21.4 lines 229–250; 1.21.5 lines 229–250). `Entity.setXRot` keeps finite pitch in `Math.clamp(pitch % 360.0F, -90.0F, 90.0F)` in both releases (lines 3513–3518). The server-side wrapper preserves this already bounded pitch. No out-of-range sum is reachable along this ordinary local correction route.
- Client correction application: `ClientPacketListener.handleMovePlayer` passes the packet's `change` and `relatives` to `setValuesFromPositionPacket`, which invokes `calculateAbsolute` and sets position, delta movement, yaw and pitch (1.21.4 lines 707–735; 1.21.5 lines 720–748). On the traced ordinary path the relative set is empty, so the helper clamp is the identity and there is no rotated-velocity calculation. Final pitch is also assigned through `Entity.setXRot` in both versions.

## Bounds

This audit covers only the named report revision and exact local Mojmap 1.21.4/1.21.5 sources for the helper math and ordinary local server movement-correction route. It does not inspect custom teleport endpoints, arbitrary relative flags, malformed or crafted packets, other correction sources, glide/fall-flying behavior, position axes, geometry, runtime parity, product code, or historical Git state. No source generation, network access, builds, tests, or launches were performed.
