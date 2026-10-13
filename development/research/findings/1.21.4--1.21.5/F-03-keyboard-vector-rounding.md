# F-03: Keyboard diagonal input is normalized in float before travel in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, getInputVector 1429-1441.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, getInputVector 1458-1470.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2766-2784.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2712-2717.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, serverAiStep 607-617.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, modifyInput 623-658; applyInput 607-621.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java`, tick 20-31.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java`, tick 22-31.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/ClientInput.java`, class declaration / field initialization 5-13.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/phys/Vec2.java`, normalized 48-51.

Difference: For simultaneous forward-plus-side keyboard input, 1.21.4 keeps two raw unit impulses through the local-player setup and reaches `Entity#getInputVector` with the scaled Vec3, which normalizes in double precision when its squared length exceeds `1.0`. 1.21.5 first normalizes the two keyboard axes as a float `Vec2`, then scales and applies the new square-movement correction before the same Vec3 consumer. The client player path is supplied by `KeyboardInput`; the source inventory found it as the only `ClientInput` subclass and both `ClientPacketListener` construction sites install it for the local player. The ideal diagonal direction and magnitude are normalized in both paths. The operation order and precision differ: 1.21.5 rounds the direction in float before the later movement speed and yaw operations, while 1.21.4 carries the scaled raw components into a double Vec3 normalization. The new correction also preserves scaled diagonal movement under the item-use and sneaking multipliers. This finding isolates the normalization precision; the separate `0.98F` input-damping difference on 1.21.5's controlled-camera path is recorded in F-09.

Movement meaning: Local key state -> `KeyboardInput#tick` -> `LocalPlayer.aiStep` input sampling -> 1.21.4 `serverAiStep` / 1.21.5 `applyInput` -> `LivingEntity.aiStep` -> player travel -> `Entity#getInputVector` -> acceleration/velocity. 1.21.4 uses the public `ClientInput` impulses; 1.21.5 reads `ClientInput.moveVector`. The source proves the precision and operation-order change. This can change low-order float/double bits in diagonal per-tick acceleration, which later travel arithmetic can propagate into position. It does not establish a measured trajectory or the size/persistence of any difference. Single-axis and zero keyboard input do not exercise diagonal normalization. Exact rounding through the downstream movement branches remains part of the open travel slices.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_21_4/KeyboardDiagonalInput.java"
    ],
    "hooks": [
      "KeyboardDiagonalInputHook"
    ],
    "limits": [
      "Old raw keyboard vector hook exists; downstream square correction and 0.98F damping are separately resolved operations, not proved by vector hook alone. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java",
      "member": "tick",
      "lines": "20-31",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/ClientInput.java",
      "member": "class declaration / field initialization",
      "lines": "5-13",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "serverAiStep",
      "lines": "607-617",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2766-2784",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "getInputVector",
      "lines": "1429-1441",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java",
      "member": "tick",
      "lines": "22-31",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/phys/Vec2.java",
      "member": "normalized",
      "lines": "48-51",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b637ef6899de5d8892e19273b114ffaf0397761e509170a56d52dd068f54bffe; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "modifyInput",
      "lines": "623-658",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "applyInput",
      "lines": "607-621",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2712-2717",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "getInputVector",
      "lines": "1458-1470",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
