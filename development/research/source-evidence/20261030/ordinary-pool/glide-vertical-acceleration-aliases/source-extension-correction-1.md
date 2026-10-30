# Correction 1: exact glide line references

This supplement corrects the broad `Glide branch` ranges in `source-extension.md`; its claims and source hashes remain unchanged. The exact per-release anchors are:

| Release | Gate `isFallFlying()` | Float narrowing | Vertical acceleration |
|---|---:|---:|---:|
| 1.9 | 1293 | 1304 | 1305 |
| 1.9.4 | 1306 | 1317 | 1318 |
| 1.10.2 | 1336 | 1347 | 1348 |
| 1.11.2 | 1392 | 1403 | 1404 |
| 1.12 | 1429 | 1440 | 1441 |
| 1.12.1 | 1429 | 1440 | 1441 |
| 1.12.2 | 1429 | 1440 | 1441 |

These line numbers refer to `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` under each exact release root. The source method begins at 1.9 line 1289, 1.9.4 line 1302, 1.10.2 line 1332, 1.11.2 line 1388, and 1.12+ line 1425.
