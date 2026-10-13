# F-S4-BORDER-EPSILON: lazy collision query drops the border shape within epsilon of its integer boundary

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/level/CollisionGetter.java`, `getBlockCollisions`, lines 78–90.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/level/CollisionGetter.java`, `noCollision`, lines 36–42.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/level/CollisionSpliterator.java`, `getChunk`, lines 102–133.

Difference: Let a collision-border face be at an integer coordinate and place the entity AABB strictly inside that face, with its nearest face less than `1.0E-7` from the integer boundary. The AABB and its deflated form remain strictly inside the border collision box, while the inflated form intersects the border shape. 1.15.2 therefore emits the nonempty border shape. In 1.16.5, `isBoxFullyWithinWorldBorder` is true because every AABB bound remains strictly between the same floor/ceiling coordinates used to construct the collision shape; the new guard skips both later tests and emits no border shape. The two `noCollision` helpers reduce this stream directly to a boolean. With no other intersecting nonempty shapes, the same supplied entity/query state can therefore produce `false` in 1.15.2 and `true` in 1.16.5.

Movement meaning: The exact endpoint sources show the query reachable from Player edge-support collision probes.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_15_2/SneakEdgeCollisionQuery.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_16/SneakEdgeCollisionQuery.java"
    ],
    "hooks": [
      "SneakEdgeCollisionQueryHook"
    ],
    "limits": [
      "SneakEdgeCollisionQueryHook copies old epsilon border inclusion and later strict floor/ceiling containment skip, but only for sneak-edge queries. Finding concerns general noCollision/collision-stream consumers too; those operations are not registered."
    ]
  },
  "applicability": [
    "Endpoint interval (1.15.2, 1.16.5]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/level/CollisionGetter.java",
      "member": "getBlockCollisions",
      "lines": [
        [
          78,
          90
        ]
      ],
      "status": "verified-content",
      "sha256": "63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.16.5",
      "mapping": "mojmap",
      "path": "1.16.5/mojmap/net/minecraft/world/level/CollisionGetter.java",
      "member": "noCollision",
      "lines": [
        [
          36,
          42
        ]
      ],
      "status": "verified-content",
      "sha256": "b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.16.5",
      "mapping": "mojmap",
      "path": "1.16.5/mojmap/net/minecraft/world/level/CollisionSpliterator.java",
      "member": "getChunk",
      "lines": [
        [
          102,
          133
        ]
      ],
      "status": "verified-content",
      "sha256": "19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/level/CollisionGetter.java",
      "member": "getBlockCollisions",
      "lines": [
        [
          79,
          90
        ]
      ],
      "status": "verified-content",
      "sha256": "63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.16.5",
      "mapping": "mojmap",
      "path": "1.16.5/mojmap/net/minecraft/world/level/CollisionGetter.java",
      "member": "getBlockCollisions",
      "lines": [
        [
          58,
          67
        ]
      ],
      "status": "verified-content",
      "sha256": "b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
