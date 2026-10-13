# F017: Auto-jump entity collision query bounds

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `autoJump`, lines 882–980.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `autoJump`, lines 857–959.
1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/world/EntityView.java`, `getEntityCollisions`, lines 21–34.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/world/EntityView.java`, `getEntityCollisions`, lines 53–64.

Difference: The paired local-player `move` overrides call `autoJump` after `super.move`, using the resulting horizontal displacement. Both auto-jump methods share the same enabled/timer/on-ground/sneak/riding/input gates and the same corridor, obstacle-height, and delayed-jump checks. 1.13.2 calls `world.getCollisions(this, box)`; 1.14.4 calls `world.getCollisions(this, box, Collections.emptySet())`. The empty excluded-entity set matches 1.13.2's default overload. Both query block and entity collision shapes. The entity-shape enumeration differs. 1.13.2 returns early only for an empty shape, asks for entities in `shape.bounds().expand(0.25)`, then retains collision shapes that intersect the original bounds. 1.14.4 returns early when the average side length is below `1.0E-7`, asks for entities in `shape.expand(1.0E-7)`, and retains shapes that intersect that epsilon-expanded box. These predicates are not identical: the returned obstacle set can differ at the epsilon boundary or when an entity collision shape extends beyond its entity-query bounds. 1.14.4 also passes an entity collision context to block shapes; the only context-reading collision provider identified here is modern-only scaffolding and is out of scope.

Movement meaning: `LocalClientPlayerEntity.move` -> superclass collision resolution -> `autoJump(actualDx,actualDz)` -> block/entity obstacle stream -> `ticksToNextAutojump = 1` when the scan finds a rise in the configured height interval -> next local `mobTick` synthesizes jump input.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F017: Auto-jump entity collision query bounds. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "autoJump",
      "lines": [
        [
          882,
          980
        ]
      ],
      "status": "verified-content",
      "sha256": "2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "autoJump",
      "lines": [
        [
          857,
          959
        ]
      ],
      "status": "verified-content",
      "sha256": "708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/world/EntityView.java",
      "member": "getEntityCollisions",
      "lines": [
        [
          21,
          34
        ]
      ],
      "status": "verified-content",
      "sha256": "2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/world/EntityView.java",
      "member": "getEntityCollisions",
      "lines": [
        [
          53,
          64
        ]
      ],
      "status": "verified-content",
      "sha256": "3822844ced53882c52f0523a1bd714baa3cd9bb9dab38a5e2c53051c7b1590e3",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
