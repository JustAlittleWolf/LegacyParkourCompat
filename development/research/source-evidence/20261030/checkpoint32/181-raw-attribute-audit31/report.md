# Raw classfile attribute audit: pinned Minecraft 1.8.1

## Scope and result

Inspected every `.class` entry in the exact ordinary mapped client archive, including attributes nested inside every `Code` attribute. The archive SHA-256 is `d2977e05623d692e2d73dacbd0d6328b9226f47d80f824711a6d5fe81df09001`, matching the requested pin. Its source client archive SHA-256 is `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd`, also matching the supplied pin.

**Verdict: no unknown or custom classfile attributes occur anywhere in the mapped archive.** Consequently none occur in the helper/owner classes under review. The inventory covers 2,500 classes and 70,588 attribute occurrences. It identifies only standard JVM attributes supported by ASM's class tree model:

| Attribute | Location | Occurrences |
|---|---|---:|
| `Code` | methods | 19,056 |
| `ConstantValue` | fields | 14 |
| `LineNumberTable` | nested in `Code` | 19,056 |
| `LocalVariableTable` | nested in `Code` | 11,552 |
| `MethodParameters` | methods | 11,907 |
| `SourceFile` | classes | 2,500 |
| `StackMapTable` | nested in `Code` | 6,503 |

The remaining arbitrary-attribute preservation caveat in the candidate review is closed for these exact inputs: default `ClassReader` behavior cannot silently omit a custom attribute that is not present in the raw archive. This does not prove preservation of unknown attributes in other inputs, nor movement parity.

## Method

`inventory_classfile_attributes.py` uses Python standard-library ZIP and binary parsing. It reads the raw classfile constant pool to resolve attribute names; parses class, field, method and class-level attribute tables; descends into each method `Code` payload and inventories its nested attribute table; and rejects malformed/trailing data. No ASM parsing was used. No tests, build, game launch, download, decompile, cache write, or product source edit was performed.

## Evidence files

- `inventory.json`: complete machine-readable inventory and unknown-name evidence.
- `inventory_classfile_attributes.py`: reproducible parser.
