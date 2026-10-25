# Legacy Parkour Compat: Projektziel und Überarbeitung der Arbeitsweise

Dieses Dokument beschreibt die gewünschte Ausrichtung des Projekts und die Anforderungen an die weitere Arbeit. Es dient als Auftrag für den anschließend ausführenden Agenten.

## 1. Projektziel und Scope

Legacy Parkour Compat ist ein Fabric-Mod, mit dem ein moderner Minecraft-Client das Bewegungsverhalten älterer Minecraft-Versionen für Parkour reproduzieren soll.

Das zentrale Ziel ist perfekte Parität der clientseitigen Spielerbewegung von Tick zu Tick: Wenn auf einem modernen Client beispielsweise das Parkour-Profil 1.8 ausgewählt ist, muss der Spieler unter denselben Ausgangsbedingungen und mit denselben Inputs nach jedem Tick dieselbe Position erreichen wie in Minecraft 1.8. Dafür müssen auch die bewegungsrelevanten Zustände und Berechnungen exakt stimmen, einschließlich historischer Bugs, Rundung und Reihenfolge mathematischer Operationen.

Entscheidend ist das Verhalten in alten Parkour-Maps. Diese bestehen überwiegend aus Bewegung und Interaktionen mit Elementen, die diese Bewegung beeinflussen.

### Im Scope

- Clientseitige Spielerbewegung und die dafür relevanten Berechnungen.
- Historische Kollisionsformen von Blöcken.
- Bewegungsrelevante Auswirkungen von Blöcken, beispielsweise Slime-Bounce.
- Auswirkungen beweglicher Elemente wie Pistons auf den Spieler.
- Bewegungseffekte von Tränken, Verzauberungen und vergleichbaren Zuständen.
- Historische Einschränkungen oder Bugs, die in Parkour-Maps verwendet wurden. Ein Beispiel ist das beschriebene Verhalten hoher Jump-Boost-Level, die in bestimmten alten Versionen das Springen verhindern konnten.
- Bewegungsregeln, die vorhandene Spielerzustände auswerten, beispielsweise den Hungerstand.
- Direkte Bewegungsreaktionen auf Ereignisse, auch wenn die Berechnung des auslösenden Ereignisses selbst außerhalb des Scopes liegt.

Historische Beispiele müssen anhand des jeweiligen Minecraft-Quellcodes geprüft werden.

### Vorerst außerhalb des Scopes

- Darstellung und rein visuelle Unterschiede.
- Technische Unterschiede bei der Erfassung von Maus- und Tastatureingaben, soweit sie lediglich die Bedienung des Programms betreffen.
- Berechnung von Hungerabbau, Heilung, Regeneration und Schaden einschließlich Fallschaden.
- Historische Regeln dafür, wann sich ein Spieler in ein Bett legen darf.
- Allgemeine Redstone-Logik, soweit es nicht um ihre konkreten Auswirkungen auf die Spielerbewegung geht.

Die Abgrenzung bei Hunger und Schaden ist wichtig: Wie ein Zustand entsteht, muss nicht historisch reproduziert werden. Wie sich dieser Zustand auf die Bewegung auswirkt, kann dagegen relevant sein.

### Umgang mit neuen Features

Die Auswahl einer alten Parkour-Version soll keine später hinzugefügten Features entfernen oder für sie ein erfundenes historisches Verhalten erzeugen.

Ein Block, den es in der ausgewählten Version noch nicht gab, soll grundsätzlich sein modernes Verhalten behalten. Als Beispiel wurde ein neuer Block wie der „Shelf-Mushroom“ genannt: Bei Auswahl von Parkour-Version 1.8 soll dessen modernes Verhalten erhalten bleiben. Die konkrete Einführungsversion solcher Beispiele ist bei der Umsetzung zu prüfen.

Ebenso soll beispielsweise das Absteigen von einem Kamel nicht auf eine historische Variante umgestellt werden, die aus einer Zeit vor der Einführung von Kamelen stammt.

Zu unterscheiden sind die Einführung eines Features und spätere Änderungen an einem bereits existierenden Feature. Bewegungsrelevante Änderungen an bestehenden Features können Gegenstand der historischen Emulation sein; ihre Einführung soll nicht rückgängig gemacht werden. Das gilt beispielsweise auch für spätere Verhaltensänderungen am Honigblock.

## 2. Aufgabenverteilung zwischen Client und Server

Die eigentlichen historischen Movement-Anpassungen sollen ausschließlich auf dem Client liegen.

Der Client erlaubt die Auswahl einer Parkour-Version. Ein Server mit dem Mod oder einem späteren Plugin kann vorgeben, dass Spieler den Mod benötigen und welches Parkour-Profil verwendet werden muss.

Der Server soll keine eigene historische Physik durch entsprechende Patches nachbilden. Stattdessen soll er für betroffene Spieler diejenigen Movement-Checks lockern oder deaktivieren, die legitime historische Bewegung zurückweisen würden.

Ein Beispiel sind unterschiedliche Kollisionsformen: Der historische Client kann eine Position erreichen, die aus Sicht der modernen Server-Kollision innerhalb eines Blocks liegt. Solche Unterschiede dürfen nicht zu unerwünschten Positionskorrekturen führen.

## 3. Repository und Entwicklungswerkzeuge aufräumen

Vor der weiteren Discovery und Implementierung soll der vorhandene Stand gesichtet, aufgeräumt und an die hier beschriebene Struktur und Arbeitsweise angepasst werden. Dazu gehören insbesondere vorhandene Patches und deren Code-Struktur.

Bei der Bestandsaufnahme ist zu berücksichtigen, dass frühere Implementierungen bereits bewusst aus dem Repository entfernt wurden, um die Qualität einer neuen, unmittelbar aus dem Minecraft-Quellcode abgeleiteten Umsetzung zu untersuchen. Deshalb muss zuerst festgestellt werden, welche Implementierungen aktuell tatsächlich vorhanden sind.

Workflow-Beschreibungen und Workflow-Ergebnisse müssen getrennt sein. Der bisherige `workflows`-Ordner war für die Beschreibung der Arbeitsweise gedacht. Findings, Kampagnenergebnisse und Fortschrittsberichte sollen dort nicht abgelegt werden. Eine eindeutigere Benennung des Ordners kann sinnvoll sein.

Auch eine klarere Trennung zwischen Produktcode und Entwicklungsinfrastruktur ist zu prüfen:

- Produktcode des Mods und später gegebenenfalls des Plugins.
- Entwicklungswerkzeuge wie TAS-Client, Parkour-Gym-Server und Testkoordination.
- Build-Werkzeuge, soweit eine gemeinsame Einordnung technisch sinnvoll ist.

Eine Bündelung der Entwicklungswerkzeuge unter einem gemeinsamen Development-Bereich ist eine mögliche Struktur, noch keine verbindlich festgelegte Ordneraufteilung. Dabei sind die technischen Besonderheiten von `buildSrc` zu berücksichtigen.

Die bisherigen Discovery-Dokumente sind häufig zu umfangreich und bürokratisch. Es ist zu entscheiden, ob sie sinnvoll vereinfacht werden können oder ob ein sauberer Neustart der Discovery besser ist. Die bevorzugte Richtung ist eine neue, schlanke Discovery statt der Übernahme unnötig komplizierter Altberichte.

## 4. Decompilierte Minecraft-Quellen

Der vorhandene Decompile-Task soll reproduzierbare Ergebnisse liefern. Bei gleicher Minecraft-Version sowie unveränderten Mappings und Werkzeugen soll derselbe Quellstand entstehen.

Bereits decompilierte Versionen sollen wiederverwendet werden, damit die aufwendige Decompilierung nicht unnötig wiederholt wird. Eine ausdrücklich erneut gestartete Decompilierung darf vorhandene Ausgabe ersetzen.

Es soll genau einen gemeinsamen, dauerhaften Ablageort auf dem Rechner geben, aus dem alle Agenten lesen. Dieser gehört weder in einzelne Worktrees noch in einen kurzlebigen Build-Ordner.

Neue Versionen werden über den vorgesehenen Decompile-Prozess ergänzt. Recherche- und Implementierungsagenten behandeln diese Quellen als schreibgeschützt.

## 5. Arbeitsabläufe und ihre Dokumentation

Es sollen vier fachliche Arbeitsabläufe beschrieben werden:

1. Unterschiede entdecken.
2. Gefundene Unterschiede implementieren.
3. Bewegungsparität testen.
4. Die Arbeit orchestrieren.

Die Discovery umfasst Quellcodevergleiche und ergänzende Recherchen in beiden Wikis. Das Testen wird erst in einer späteren Phase im Detail ausgearbeitet.

Für die Orchestrierung müssen Haupt-Orchestrator und Unter-Orchestratoren ausdrücklich unterschieden werden. Zunächst ist zu prüfen, ob die vorhandenen Workflows diese Unterscheidung bereits ausreichend beschreiben.

Unter-Orchestratoren benötigen klar dokumentierte Regeln. Diese können als eigener kurzer Workflow oder als eindeutig abgegrenzter Teil des Orchestrierungsworkflows formuliert werden. Gemeinsame Regeln sollen referenziert statt mehrfach kopiert werden.

Die Workflows sollen kleine, nachvollziehbare Arbeitspakete ermöglichen und ohne umfangreiche zusätzliche Verwaltungsdokumentation fortsetzbar sein.

## 6. Discovery durch Quellcodevergleich

Die Discovery soll Unterschiede in der für die clientseitige Spielerbewegung relevanten Minecraft-Logik systematisch finden.

Verglichen werden jeweils zwei konkrete Versionen mit kompatiblen Mapping-Namensräumen. Yarn und Legacy Yarn sowie Mojang-Mappings und unobfuskierter Quellcode sind dabei als mögliche zusammengehörige Familien zu berücksichtigen. Übergangsbereiche, insbesondere ungefähr um Version 1.14, müssen gesondert betrachtet werden.

Vor den vielen Einzelvergleichen soll ein leistungsfähigerer Agent möglichst einmal eine grobe Übersicht der relevanten Quellcodebereiche erarbeiten. Dazu gehören beispielsweise:

- Entity-Tick und grundlegende Bewegung.
- Client-Player-Tick und beteiligte Spielerlogik.
- Blockkollisionen und Kollisionsformen.
- Blockeffekte auf Bewegung.
- Bewegungsrelevante Einflüsse von Pistons und anderen Mechaniken.

Diese Eingrenzung darf nicht so eng sein, dass indirekte Einflüsse übersehen werden. Reine Rendering-Logik ist dagegen nicht relevant.

Ob ein Agent ein vollständiges Versionspaar untersucht oder ein Unter-Orchestrator den Vergleich nach wiederkehrenden Bereichen aufteilt, soll anhand sinnvoller Aufgabengrößen entschieden werden. Ein einfacher Versionspaar-Koordinator soll ebenfalls ein kosteneffizientes Modell verwenden können; eine koordinierende Rolle allein erfordert noch kein besonders leistungsfähiges Modell.

Eine gefilterte Sicht auf relevante Quellen könnte hilfreich sein. Ob sie tatsächlich benötigt wird, bleibt offen; die gemeinsamen Originalquellen sollen dabei nicht verändert werden.

### Format der Findings

Für jede unabhängige Änderung soll ein kurzes Markdown-Dokument entstehen. Es enthält:

- Die konkreten verglichenen Minecraft-Versionen und verwendeten Mappings.
- Verweise auf die relevanten Quelldateien und Stellen in beiden Versionen.
- Eine knappe Erklärung des Unterschieds und seiner Bedeutung für die Spielerbewegung.

Quellpfade können relativ zur jeweiligen Version und zum Mapping angegeben werden. Im Text sollen konkrete Versionsnamen stehen, keine künstlichen Bezeichnungen wie „Version A“ und „Version B“.

Nicht benötigt werden standardmäßig Dateihashes, lange Handoff-Texte oder Felder wie „Classification“, „Confidence“, „Applicability“, „First Change Release“ und „Runtime Validation“.

Findings sollen zentral gesammelt werden. Welche Bereiche bereits untersucht wurden und welche Änderungen umgesetzt sind, wird ebenfalls zentral und möglichst knapp verwaltet. Diese Informationen sollen nicht in jedem Finding wiederholt werden.

Doppelte Arbeit muss vermieden werden, ohne jedem Agenten lange Listen aller bereits untersuchten Dateipfade als Kontext mitzugeben.

## 7. Discovery anhand der Wikis

Zusätzlich zum direkten Quellcodevergleich sollen das Minecraft Parkour Wiki und das Minecraft Wiki ausgewertet werden.

### Minecraft Parkour Wiki

Das Minecraft Parkour Wiki bietet besonders für ältere Versionen eine kompakte Übersicht wichtiger Parkour-Änderungen.

Die einzelnen Punkte sollen betrachtet und anschließend im decompilierten Minecraft-Quellcode nachvollzogen werden. Aus dem Quellcodebeleg entsteht ein Finding im selben knappen Format wie bei der direkten Discovery.

Aufgrund des begrenzten Umfangs kann hier ein Agent pro aufgeführter Änderung sinnvoll sein.

### Minecraft Wiki

Im Minecraft Wiki sollen die Versionsseiten nach bewegungsrelevanten Änderungen durchsucht werden. Besonders die Bugfix-Listen können wichtige Hinweise liefern.

Zunächst werden die relevanten Änderungen einer Seite gesammelt. Anschließend werden sie im Quellcode belegt.

Zusammenhängende Änderungen sollen zu sinnvollen Recherchepaketen gebündelt werden. Eine Seite mit zwanzig relevanten Punkten braucht nicht automatisch zwanzig Agenten; wenige nach Themen aufgeteilte Aufträge können effizienter sein.

Die Wikis liefern Hinweise. Die genaue technische Änderung muss anhand des Minecraft-Quellcodes nachvollziehbar sein.

## 8. Implementierung der gefundenen Änderungen

Implementierungsagenten sollen eng abgegrenzte Aufgaben auf eigenen Branches bearbeiten, beispielsweise eine einzelne Änderung oder eine sinnvolle Gruppe innerhalb einer Version.

Gewünscht ist eine unabhängige Implementierung: Die Agenten dürfen die allgemeine Projektstruktur und die notwendige Infrastruktur kennen, sollen aber vorhandene konkrete Patches, Mixins, Hooks und bisherige Verhaltensimplementierungen nicht als Vorlage sehen.

Wie diese eingeschränkte Sicht praktisch hergestellt wird, muss im Workflow festgelegt werden.

Die Ergebnisse werden anschließend durch einen Integrationsagenten oder einen dafür zuständigen Orchestrator zusammengeführt. Dabei müssen doppelte Implementierungen und redundanter Code bereinigt werden.

### Code-Struktur

- Mixins greifen an den Stellen ein, an denen Verhalten verändert werden muss.
- Jeder unabhängig aufzulösende technische Eingriff erhält ein eigenes Interface.
- Eine Change-Klasse kann mehrere solcher Interfaces implementieren, wenn diese zu derselben Änderung gehören.
- Die Zuordnung zur historischen Version erfolgt über geeignete Metadaten, beispielsweise eine Annotation.
- Eine Änderung soll möglichst in einer Datei gekapselt sein.
- Hooks sollen durch sinnvolle Packages gegliedert werden.
- Der Name „Behavior“ soll durch eine passendere Bezeichnung ersetzt werden, soweit er noch verwendet wird.
- Die Registrierung der Changes darf explizit im Code stehen. Eine dynamische Auflistung über Fabric-Metadaten ist dafür nicht erforderlich.
- Kommentare an den Changes sollen auf die relevanten Stellen des ursprünglichen Minecraft-Quellcodes in beiden verglichenen Versionen verweisen.

Mathematisch ähnliche Berechnungen reichen nicht aus. Operationsreihenfolge, Datentypen, Rundung und historische Besonderheiten müssen erhalten bleiben.

### Automatische Auswahl der Changes

Die Laufzeit soll für jedes Interface unabhängig die passende historische Implementierung auswählen.

Ein vereinfachtes Beispiel: Eine Operation liefert in Version 1.8 den Wert 1, in 1.9 den Wert 2 und ab 1.10 bis zur aktuellen Version den Wert 3. Dann werden zwei historische Changes benötigt: einer für das Verhalten von 1.9 und einer für das Verhalten von 1.8. Beide implementieren dasselbe Interface.

Bei Auswahl eines Profils wird automatisch die passende Variante verwendet. Für unverändertes modernes Verhalten ist kein zusätzlicher Change nötig.

### Fortschritt

Jedes Finding soll nur einmal implementiert werden. Dafür reicht eine schlanke zentrale Übersicht, beispielsweise eine Markdown-Datei mit Verweisen auf bereits abgeschlossene Findings.

## 9. Orchestrierung und Modellwahl

Der Haupt-Orchestrator soll die freigegebenen Projektphasen nach einer anfänglichen Klärung autonom durchführen.

Zu Beginn soll er den Auftrag durchdenken, auf fehlende wichtige Anforderungen prüfen und offene Fragen möglichst gesammelt stellen. Dabei soll er auch auf Widersprüche oder Aspekte hinweisen, die in dieser Beschreibung möglicherweise übersehen wurden.

Danach soll er die laufende Arbeit selbst koordinieren und Fragen der Arbeitsagenten beantworten.

Seine Hauptaufgabe ist Koordination. Recherche, Implementierung und auch Verbesserungen an fehlerhaften Workflow-Beschreibungen soll er weitgehend delegieren.

Für die Hauptkoordination ist ein leistungsfähigeres Modell vorgesehen, beispielsweise GPT-6.1. Für die meisten klar umrissenen Arbeitsaufträge ist ein kosteneffizientes Modell mit gutem Reasoning gewünscht; als aktuelles Beispiel wurde GPT-6 Luna mit Reasoning-Stufe High genannt.

Diese Modellnamen sollen nicht dauerhaft in den Workflow eingebaut werden. Maßgeblich ist ein geeignetes Verhältnis zwischen Kosten und Leistungsfähigkeit. Komplexere Management-Aufgaben können ein stärkeres Modell rechtfertigen.

### Arbeitsagenten und Unter-Orchestratoren

Der Haupt-Orchestrator muss bei jeder Delegation ausdrücklich unterscheiden:

- Ein Arbeitsagent erledigt seine zugewiesene Aufgabe selbst und darf keine weiteren Agenten erstellen.
- Ein Unter-Orchestrator darf seine zugewiesene Aufgabe innerhalb ausdrücklich festgelegter Grenzen an weitere Agenten delegieren.

Ohne ausdrückliche Delegationserlaubnis ist ein Agent ein Arbeitsagent.

Der Auftrag an einen Unter-Orchestrator muss festlegen:

- Welchen fachlichen Bereich er verantwortet.
- Welche Aufgaben er weiterdelegieren darf.
- Wie viele zusätzliche Agenten er gleichzeitig einsetzen darf.
- Ob er ausschließlich Arbeitsagenten oder auch weitere Unter-Orchestratoren erstellen darf.
- Welches Zeitbudget und welche Abschlussanforderungen gelten.

Weitere Hierarchieebenen sollen nur mit ausdrücklicher Erlaubnis entstehen. Ein Unter-Orchestrator darf seine Delegationsrechte oder Kapazitätsgrenzen nicht selbst erweitern.

Für Unter-Orchestratoren gelten grundsätzlich dieselben Regeln wie für den Haupt-Orchestrator: klare Aufträge, passende Modellwahl, vorausschauendes Scheduling, schlanke Fortschrittsverwaltung und geordneter Abschluss. Ihr Auftrag ist jedoch auf den zugewiesenen Teilbereich begrenzt.

Fragen, die sie innerhalb dieses Bereichs entscheiden können, beantworten sie selbst. Übergreifende Fragen geben sie an den übergeordneten Orchestrator weiter.

### Subagenten und Parallelität

Bevor die technische Orchestrierung endgültig festgelegt wird, soll der ausführende Agent prüfen, wie viele Subagenten tatsächlich parallel möglich sind. Konkret soll versucht werden, zwanzig Subagenten über das Subagenten-Tool zu starten und das verfügbare Parallelitätslimit festzustellen.

Das Subagenten-Tool wird gegenüber vielen separaten Chats bevorzugt. Viele parallel laufende Chats haben in der bisherigen Nutzung zu Problemen mit der App geführt.

Als gewünschte Größenordnung wurden etwa fünfzehn gleichzeitig aktive delegierte Agenten genannt, sofern die Umgebung das unterstützt. Dieses Budget gilt global, einschließlich Unter-Orchestratoren und ihrer Arbeitsagenten. Das tatsächliche technische Limit der Umgebung muss dabei eingehalten werden.

Ein Unter-Orchestrator könnte beispielsweise höchstens vier eigene Arbeitsagenten betreuen. Er selbst und diese vier Agenten würden zusammen fünf Plätze des globalen Pools belegen. Bei einem Budget von fünfzehn blieben zehn Plätze für andere delegierte Agenten.

Der Haupt-Orchestrator muss Kapazitäten so zuteilen, dass Unter-Orchestratoren den globalen Pool nicht unabhängig voneinander überbuchen. Die dafür erforderliche Übersicht soll knapp bleiben.

### Aufträge und Kommunikation

Jeder Agent erhält die für seine Aufgabe relevanten Informationen sowie klare Angaben zu Vorgehen, Grenzen und erwartetem Ergebnis. Dazu gehört auch die ausdrückliche Angabe, ob er selbst weitere Agenten erstellen darf.

Die Aufgaben sollen ausreichend konkret beschrieben sein, beispielsweise: relevante Dateien eines Packages prüfen, mit den entsprechenden Dateien der anderen Version vergleichen, bewegungsrelevante Unterschiede identifizieren und knapp belegen.

Arbeitsaufträge sollen direkt formuliert sein. Statt „Der Nutzer hat gesagt, du sollst pausieren“ genügt „Pausiere“. Gesprächshistorie und unnötige Hinweise auf die Agentenhierarchie gehören nicht in die Aufträge. Ebenso ist keine künstliche Behauptung nötig, der Orchestrator sei der Nutzer.

Die notwendigen Zuständigkeiten und Delegationsgrenzen müssen trotzdem eindeutig sein.

### Scheduling

Es soll möglich sein, nur Discovery oder Discovery und Implementierung gemeinsam zu beauftragen.

Wenn beide Phasen aktiv sind, haben umsetzbare Implementierungsaufgaben bei der nächsten freien Kapazität Vorrang. Bereits laufende Aufgaben werden dafür nicht unterbrochen.

Der Orchestrator soll kommende Aufgaben vorbereiten, während andere Agenten arbeiten, damit freie Kapazitäten zeitnah genutzt werden können.

### Zeitlimits, Pausen und Fortsetzung

Die Arbeit muss in mehreren Sitzungen fortsetzbar sein. Der vorhandene Dokumentationsstand soll erkennen lassen, was abgeschlossen ist und was noch aussteht; ein zusätzliches umfangreiches Checkpoint-Format ist nicht gewünscht.

Bei einem vorgegebenen Zeitbudget ist die Arbeit so zu planen, dass am Ende keine delegierten Aufgaben mehr laufen. Der Orchestrator muss rechtzeitig aufhören, neue Aufgaben zu starten, und genügend Zeit für Abschluss und Zusammenführung einplanen. Das gilt ebenso für Unter-Orchestratoren.

Eine ausdrückliche Pausenanweisung muss an die betroffenen Agenten weitergegeben werden. Der Arbeitsstand muss anschließend eine nachvollziehbare Fortsetzung erlauben.

## 10. Spätere Verifikation durch Recordings und Playback

Die Testphase gehört zum Projektziel, wird aber erst später detailliert ausgearbeitet und umgesetzt. Zunächst soll die Implementierung einen Stand erreichen, den ich als Parkour-Experte manuell prüfen kann.

Die vorgesehene Testidee beruht auf aufgezeichneten Eingaben und echten Minecraft-Clients.

Ich nehme eine Bewegung auf, beispielsweise:

- Einmal springen.
- Im Wasser schwimmen.
- Einen längeren Parkour durchlaufen.

Kurze Bewegungen dienen als gezielte Einzelprüfungen, längere Abläufe als Integrationsprüfungen. Auch die kurzen Prüfungen basieren auf echter Aufnahme und Wiedergabe der Bewegung.

### Zuerst die Aufnahme- und Wiedergabekette prüfen

Eine Aufnahme muss zunächst in derselben Minecraft-Version wiedergegeben werden. Aufnahme und Playback sollen dieselbe Bewegung erzeugen.

Auch wiederholtes Playback muss deterministisch sein. Bereits kleinste Gleitkommaabweichungen sind relevant; gemeint sind auch Unterschiede an der letzten darstellbaren Stelle, also auf ULP-Ebene.

Dieser Schritt ist besonders wichtig, weil bereits minimale Positionsunterschiede zwischen Aufnahme und Wiedergabe beobachtet wurden.

### Referenzen und Varianten

Aus einer einmal aufgenommenen Eingabefolge sollen Agenten sinnvolle Varianten erzeugen können:

- Unterschiedliche Minecraft-Versionen.
- Blöcke mit unterschiedlichen Bewegungseigenschaften.
- Positionen auf Blöcken oder an deren Rändern.
- Trankeffekte, beispielsweise Jump Boost.
- Relevante Ausrüstung und Verzauberungen, beispielsweise Soul Speed.

Ich möchte dafür nicht jede Kombination manuell konfigurieren. Ich gebe die Bewegung und den gewünschten Variantenumfang vor. Die Agenten wählen und konfigurieren passende Fälle.

Blöcke mit identischem relevantem Verhalten müssen nicht redundant geprüft werden.

Die Referenzbewegungen werden in den jeweiligen historischen Minecraft-Versionen mit dem Recording-/Playback-Mod aufgenommen, ohne die historische Emulation von Legacy Parkour Compat.

Anschließend werden dieselben Eingaben auf dem modernen Client mit Legacy Parkour Compat und dem passenden historischen Profil abgespielt. Die Bewegung muss in jedem Tick mit der historischen Referenz übereinstimmen.

Diese Prüfungen sollen als Tests im eigentlichen Mod-Projekt eingebunden werden. Sie sollen die Aufnahme- und Playback-Infrastruktur ansteuern, das passende Profil auswählen und den Vergleich mit den historischen Referenzbewegungen ausführen. Die genaue technische Integration wird in der späteren Testphase festgelegt.

### Parkour-Gym und TAS-Client

Der TAS-Client übernimmt Aufnahme und Wiedergabe.

Der Parkour-Gym-Server stellt eine einheitliche Testwelt bereit. Movement-Checks sind dort deaktiviert, und jeder Client erhält eine isolierte virtuelle Welt, damit parallele Durchläufe sich nicht beeinflussen.

Die Parkour-Strukturen werden überwiegend von mir gebaut. Ergänzend können standardisierte Flächen mit unterschiedlichen Blöcken für gezielte Varianten verwendet werden.

Die Testausführung soll möglichst parallel erfolgen. Als vorläufige Größenordnung wurden etwa fünf Minecraft-Clients mit ungefähr drei Gigabyte Speicher pro Client genannt. Die tatsächliche Belastbarkeit muss später geprüft und die vorhandene Infrastruktur gegebenenfalls angepasst werden.

Erst diese nachgewiesene Tick-für-Tick-Parität schafft die Grundlage dafür, den Mod auf Parkour-Servern einzusetzen, bei denen exaktes historisches Verhalten entscheidend ist.

## 11. Vorgeschlagene Reihenfolge der weiteren Arbeit

Die folgende Reihenfolge fasst die beschriebenen Abhängigkeiten zusammen. Sie ist eine Arbeitsgrundlage; unabhängige Schritte können sinnvoll parallelisiert werden.

1. Vorhandenen Projektstand prüfen, fehlende wichtige Anforderungen identifizieren und offene Fragen gesammelt klären.
2. Repository, vorhandene Patches und Dokumentation an die gewünschte Struktur anpassen.
3. Die gemeinsam genutzte Ablage der decompilierten Quellen sicherstellen.
4. Die verfügbare Subagenten-Parallelität prüfen.
5. Schlanke Workflows für Discovery, Implementierung und Orchestrierung festlegen, einschließlich klarer Regeln für Arbeitsagenten und Unter-Orchestratoren.
6. Den Umgang mit bisherigen Findings entscheiden und die Discovery entsprechend neu aufsetzen.
7. Die jeweils beauftragten Phasen autonom bearbeiten, gegebenenfalls Discovery und Implementierung parallel.
8. Einen ausreichend vollständigen Implementierungsstand durch mich manuell beurteilen lassen.
9. Anschließend die automatisierte Testphase konkretisieren und die Bewegungsparität nachweisen.

Noch offene Gestaltungsfragen sollen vor der autonomen Ausführung geklärt werden. Dazu gehören insbesondere die genaue Ordnerstruktur, die Aufteilung der Discovery-Aufträge, die unabhängige Sicht der Implementierungsagenten und der Umgang mit bisherigen Findings.