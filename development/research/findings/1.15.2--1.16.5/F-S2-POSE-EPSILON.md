# F-S2-POSE-EPSILON: 1.16.5 shrinks the candidate box for pose clearance

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `canEnterPose`, lines 1605–1607.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `canEnterPose`, lines 1640–1642.

Difference: 1.15.2 `Entity#canEnterPose(pose)` submits `getBoundingBoxForPose(pose)` directly to `level.noCollision`. 1.16.5 submits that candidate box after `.deflate(1.0E-7)`. AABB deflation shrinks the candidate on all axes by the supplied epsilon. Player pose selection calls this predicate for swimming eligibility, its selected pose, and crouching fallback; the method body and branch order are otherwise the same between endpoints. When a collision shape overlaps only the thin boundary removed by 1.16.5's deflation, the two calls give the collision query different candidate boxes and may select different poses. Once a pose is written to synced entity data, the unchanged listener refreshes dimensions, eye height and the bounding box.

Movement meaning: The finding is restricted to the player pose-entry predicate and candidate AABB. The same canEnterPose predicate also supplies same-tick crouch input slowdown; adjacent cache writer/reader introduces no tick delay.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-S1-CROUCH-SLOWDOWN-EPSILON: pose-clearance epsilon changes keyboard slowdown. Existing unrelated later operations do not establish coverage.",
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-S2-POSE-EPSILON: 1.16.5 shrinks the candidate box for pose clearance. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.15.2, 1.16.5]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "canEnterPose",
      "lines": [
        [
          1605,
          1607
        ]
      ],
      "status": "verified-content",
      "sha256": "191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.16.5",
      "mapping": "mojmap",
      "path": "1.16.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "canEnterPose",
      "lines": [
        [
          1640,
          1642
        ]
      ],
      "status": "verified-content",
      "sha256": "f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
