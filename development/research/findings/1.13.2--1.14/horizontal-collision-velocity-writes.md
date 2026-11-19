# Captured velocity in horizontal collision writes

1.13.2, Ornithe Feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `Entity.move`, lines 696–703.

1.14, Ornithe Feather: `1.14/ornithe-feather/net/minecraft/entity/Entity.java`, `Entity.move`, lines 480–488, and `m_28162558` / `m_32166403`, lines 2589–2595.

Both releases compare each post-sneak requested horizontal component against the collision result with raw `!=`. In 1.13.2, each comparison clears its own velocity field. In 1.14, both setters read the velocity captured before either comparison. The X setter writes `(0, capturedY, capturedZ)`. The following Z setter writes `(capturedX, capturedY, 0)`.

Ordinary local-player travel supplies the current velocity to `Entity.move` through `LivingEntity.moveRelative`. If both horizontal components clip at the inside corner of two walls, 1.13.2 clears both and 1.14 preserves the captured X component. For example, start with a player box centered horizontally at `(0, 0)` with half-width about `0.3`. With no step admission, let the overlapping-height blocks at horizontal cells `(1, 0)` and `(0, 1)` obstruct the request `(0.8, 0, 0.9)`. Either axis order clips both components to roughly `0.7`, so the write order determines the retained velocity. If only Z clips, both releases retain X. The [collision axis order](../1.13.2--1.14.4/collision-axis-order.md) can independently change which components clip.

The captured writes and raw gates persist through exact 1.18.1. The line references are to `Entity.move` in `net/minecraft/entity/Entity.java` for Yarn and `net/minecraft/world/entity/Entity.java` for Mojmap.

| Releases | Mapping | Raw gates and writes |
| --- | --- | --- |
| 1.14.1 | Yarn | 480–487 |
| 1.14.2 | Yarn | 482–489 |
| 1.14.3 | Yarn | 489–496 |
| 1.14.4 | Mojmap | 494–501 |
| 1.15, 1.15.1, 1.15.2 | Mojmap | 485–492 |
| 1.16, 1.16.1 | Mojmap | 503–510 |
| 1.16.2, 1.16.3, 1.16.4, 1.16.5 | Mojmap | 519–526 |
| 1.17, 1.17.1 | Mojmap | 570–577 |
| 1.18, 1.18.1 | Mojmap | 583–590 |

1.18.2 Mojmap `Entity.move`, lines 579–581 and 597–600, uses one combined write from the existing `!Mth.equal` collision flags. Both X and Z clear when both flags are true. A nonzero clip within the widened `1.0E-5F` threshold can trigger a raw gate before 1.18.2 without triggering the corresponding 1.18.2 flag.
