# Client presentation-path closure: 1.18.2–1.19.4

Scope is changed Mojmap paths under `net/minecraft/client/gui/**`, `renderer/**`, `model/**`, `particle/**`, and `sounds/**` across the five adjacent edges. Prior client player/input/tick/network/respawn audits were not repeated.

## Coverage and source identity

The source path-role ledger contains **996 edge/path/status rows across 549 unique paths** in this scope. Row status counts by edge:

| Edge | Paths | Modified | Added | Removed |
|---|---:|---:|---:|---:|
| 1.18.2→1.19 | 278 | 252 | 22 | 4 |
| 1.19→1.19.1 | 77 | 59 | 14 | 4 |
| 1.19.1→1.19.2 | 2 | 2 | 0 | 0 |
| 1.19.2→1.19.3 | 333 | 282 | 41 | 10 |
| 1.19.3→1.19.4 | 306 | 249 | 49 | 8 |

Unique-path counts are GUI 264, renderer 207, model 43, particle 29, and sounds 6. Every file present in each versioned root was hashed into `source-hashes.tsv`. Identity roots are the exact Mojmap roots already validated against ready markers/manifests by the parent inventory and independent auditor:

| Version | Source file count | `mojmap.sources.sha256` digest |
|---|---:|---|
| 1.18.2 | 4,236 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | 4,430 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | 4,480 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |
| 1.19.2 | 4,480 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | 4,618 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | 4,740 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |

## Movement-facing references found and disposition

Searches across the changed members found no changed ordinary LocalPlayer movement writer, input reader, movement callback, or physics invocation in these presentation directories. The movement-facing symbol hits are limited to:

- `net/minecraft/client/gui/screens/DemoIntroScreen.java`: reads `Options.keyJump` only to display the translated key in demo help text.
- `net/minecraft/client/model/OcelotModel.java`: reads entity `isSprinting()` only to choose a model animation.
- GUI control screens and `KeyBindsList` update/display keybinding preferences. They do not run a changed tick/input consumer. Raw-input comparison holds the resulting input stream equal, so preference editing is not a movement-physics delta.
- Player-specific references in `CreativeModeInventoryScreen`, `SpellParticle`, `SignRenderer`, `ItemRenderer`, `LivingEntityRenderer`, `FogRenderer`, `CompassItemPropertyFunction`, `ItemProperties`, `ItemInHandRenderer`, and `LevelRenderer` are read-only GUI/render/particle presentation paths (inventory display, particles, skin/pose/hand/item rendering, camera fog, compass, or render filtering). These paths do not write LocalPlayer input, movement flags/velocity/position, or movement callbacks.
- `Particle` and particle subclasses call their own particle movement/update methods; they do not dispatch player movement.

All other changed paths in this bounded namespace slice are GUI layout/text/navigation, render state/geometry/culling, model animation, particle presentation, or sounds. `isSprinting` references found in model/render methods affect presentation only. No member in this slice is a candidate.

## Limits

This closes only the five client presentation namespaces listed above. It is not a full changed-member audit of the 5,074-row source roster and does not establish pair freeze. GUI keybinding preferences are noted but assessed under the raw-input assumption, not as a change to the shared input provider.
