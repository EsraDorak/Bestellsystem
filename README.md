# Projekt: *se1.bestellsystem*


## Inhalt
1. [Projektstruktur](#1-projektstruktur)
2. [Installieren der Abhängigkeiten](#2-installieren-der-abhängigkeiten)
3. [Sourcen des Projekts](#3-sourcen-des-projekts)
4. [Kompilieren des Quellcodes](#4-kompilieren-des-quellcodes)
5. [Ausführen des Programms](#5-ausführen-des-programms)
6. [Kompilieren der JUnit-Tests](#6-kompilieren-der-junit-tests)
7. [Ausführen der JUnit-Tests](#7-ausführen-der-junit-tests)
8. [Erzeugung der Javadoc](#8-erzeugung-der-javadoc)


&nbsp;

---

## 1. Projektstruktur

Die Struktur des Projekts ist:

```
<se1.bestellsystem>       ; Projekt-Verzeichnis
  +-<.vscode>             ; settings.json für VSCode
  |   +-- settings.json
  |   +-- .classpath.init ; CLASSPATH init-File für VSCode und eclipse
  |   +-- .project.init   ; Projekt init-File für VSCode und eclipse
  |
  +-- .classpath          ; aktives CLASSPATH-File
  +-- .project            ; aktives Projekt-File
  +-- .env.sh             ; File für das Sourcen des Projekts mit: source .env.sh
  |
  +-<.git>                ; lokales git-Repository
  |   +-- config          ; lokale git-Konfigurationsdatei
  +-- .gitignore          ; lokale Files, die git ignorieren soll
  |
  +-- tests.run           ; Liste auszuführender JUnit-Tests
  |
  +-<src>                       ; Java-Quellcode für das Projekt
  |   +-- module-info.java      ; modulares Java-Projekt
  |   |
  |   +-<application>           ; Java-Paket
  |       +-- Application.java      ; Klasse mit main()-Funktion
  |       +-- package-info.java     ; javadoc für Paket
  |   |
  |   +-<datamodel>             ; weiteres Java-Paket
  |       +-- Customer.java         ; Klasse für Kunden
  |       +-- package-info.java     ; javadoc für Paket
  |
  +-<test>                ; Java-Quellcode für JUnit-Tests
  |   +-<application>
  |       +-- Application_0_always_pass_Test.java   ; JUnit Test-Klasse
  |       +-- ...
  |
  +-<lib>           ; Bibliotheken (libraries) für die JUnit5 Testausführung
  |   +-- junit-platform-console-standalone-1.9.2.jar
  |   |
  |   +-<modules>   ; benötigte Module für module-path
  |       +-- junit-jupiter-api-5.9.3.jar ...
  |
  +--<bin, out>     ; Übersetzter Code (eclipse/VSCode: bin, IntelliJ: out)
  |   +--<application>
  |       +--Application.class  ...
```

Clonen Sie das Projekt aus Github (dazu müssen Sie sich im übergeordneten
Verzeichnis befinden, da `clone` das Projekt dort anlegt) oder downloaden Sie das
[.zip](https://gitlab.bht-berlin.de/sgraupner/se1.bestellsystem/-/archive/main/se1.bestellsystem-main.zip).

```
git clone git@gitlab.bht-berlin.de:sgraupner/se1.bestellsystem.git

cd se1.bestellsystem            ; Wechsel in das Projektverzeichnis
```


&nbsp;

---

## 2. Installieren der Abhängigkeiten

Installieren Sie die Abhängigkeiten (dependencies) für das Projekt.
Abhängigkeiten sind Bibliotheken (libraries, .jar-Files), die für das Projekt benötigt werden.
Abhängigkeiten werden aus dem [Maven-central Repository](https://mvnrepository.com) geladen,
einem weltweiten Repository mit öffentlich verfügbaren .jar-Files.

Folgende Abhängigkeiten werden für das Projekt benötigt:

- Test-Runner für JUnit-Tests im Verzeichnis: `lib`

  - [junit-platform-console-standalone-1.9.2.jar](https://mvnrepository.com/artifact/org.junit.platform/junit-platform-console-standalone),
    ( [.jar](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar) )

- sowie benötigte Module im Verzeichnis: `lib/modules`

  - [junit-jupiter-api-5.9.3.jar](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api),
    ( [.jar](https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.9.3/junit-jupiter-api-5.9.3.jar) )

  - [junit-platform-commons-1.9.3.jar](https://mvnrepository.com/artifact/org.junit.platform/junit-platform-commons),
    ( [.jar](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.9.3/junit-platform-commons-1.9.3.jar) )

  - [apiguardian-api-1.1.2.jar](https://mvnrepository.com/artifact/org.apiguardian/apiguardian-api),
    ( [.jar](https://repo1.maven.org/maven2/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar) )

  - [opentest4j-1.2.0.jar](https://mvnrepository.com/artifact/org.opentest4j/opentest4j),
    ( [.jar](https://repo1.maven.org/maven2/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar) )

Build-Werkzeuge (z.B. [maven](https://maven.apache.org/)) werden später verwendet, um die
Abhängigkeiten automatisch zu installieren.

Manuell kann man die .jar-Files mit einem download tool, z.B.
[wget](https://man7.org/linux/man-pages/man1/wget.1.html)
in die Verzeichnisse `lib` bzw. `lib/modules` installieren.

Ist das Kommando `wget` nicht vorhanden (command not found, z.B. bei GitBash), können Sie `wget`
[nachinstallieren](https://gist.github.com/evanwill/0207876c3243bbb6863e65ec5dc3f058)
oder das Kommando [curl](https://man7.org/linux/man-pages/man1/curl.1.html)
verwenden (siehe Bsp. unten).

Führen Sie die folgenden Kommandos im Projektverzeichnis aus:

```
wget -P lib \
    https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar

wget -P lib/modules \
    https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.9.3/junit-jupiter-api-5.9.3.jar \
    https://repo1.maven.org/maven2/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar \
    https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.9.3/junit-platform-commons-1.9.3.jar \
    https://repo1.maven.org/maven2/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar
```

Beispiel des Downloads mit [curl](https://man7.org/linux/man-pages/man1/curl.1.html), falls
[wget](https://man7.org/linux/man-pages/man1/wget.1.html) nicht vorhanden ist (für alle .jar-Files ausführen):

```
curl -kLSs https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.9.3/junit-jupiter-api-5.9.3.jar \
    -o lib/modules/junit-jupiter-api-5.9.3.jar
```

Alternativ können die .jar-Files in den jeweiligen Versionen auch von den URL direkt geladen werden (URL im Browser eingeben).


Prüfen Sie die jetzt vorhandenen .jar-Dateien:

```
ls -laR lib                     ; Ausgabe aller Dateien unter ./lib

lib:
total 2568
drwxr-xr-x+ 1 svgr2 Kein       0 May 14 19:30 .
drwxr-xr-x+ 1 svgr2 Kein       0 May 14 18:42 ..
-rw-r--r--  1 svgr2 Kein 2614420 Jan 10 12:01 junit-platform-console-standalone-1.9.2.jar
drwxr-xr-x  1 svgr2 Kein       0 May 14 19:30 modules

lib/modules:
total 332
drwxr-xr-x  1 svgr2 Kein      0 May 14 19:30 .
drwxr-xr-x+ 1 svgr2 Kein      0 May 14 19:30 ..
-rw-r--r--  1 svgr2 Kein   6806 Jun 27  2021 apiguardian-api-1.1.2.jar
-rw-r--r--  1 svgr2 Kein 207763 Apr 26 08:27 junit-jupiter-api-5.9.3.jar
-rw-r--r--  1 svgr2 Kein 102989 Apr 26 08:27 junit-platform-commons-1.9.3.jar
-rw-r--r--  1 svgr2 Kein   7653 Jun  6  2019 opentest4j-1.2.0.jar
```


&nbsp;

---

## 3. Sourcen des Projekts

Beim Sourcen des Projektes werden in einer Shell (im Terminal) Umgebungsvariablen
gesetzt durch Ausführen einer Datei: `.env.sh`. Hier wird die Variable CLASSPATH gesetzt.

```
source .env.sh                  ; sourcen des Projektes durch Ausführen von .env.sh

echo $CLASSPATH                 ; Anzeige des gesetzten Werts der Umgebungsvariablen
```

Für Windows wird ";" als Trennzeichen (separator) verwendet, sonst ":" (Mac, Linux):

```
CLASSPATH für Mac, Linux:
src:test:bin:lib/junit-platform-console-standalone-1.9.2.jar

CLASSPATH für Windows:
src;test;bin;lib/junit-platform-console-standalone-1.9.2.jar
```

Als weitere Aktion werden aus dem Verzeichnis
[.vscode](https://gitlab.bht-berlin.de/sgraupner/se1.bestellsystem/-/tree/main/.vscode)
die Files
[.project.init](https://gitlab.bht-berlin.de/sgraupner/se1.bestellsystem/-/blob/main/.vscode/.project.init)
und
[.classpath.init](https://gitlab.bht-berlin.de/sgraupner/se1.bestellsystem/-/blob/main/.vscode/.classpath.init)
in das Projektverzeichnis kopiert (nur beim ersten Aufruf).

Dies ist für VSCode und eclipse für das Erkennen des Projekts als modulares Projekt
(mit [module-info.java](https://gitlab.bht-berlin.de/sgraupner/se1.bestellsystem/-/blob/main/src/module-info.java))
erforderlich.


&nbsp;

---

## 4. Kompilieren des Quellcodes

Das Übersetzen des Quellcodes erfolgt mit dem Java-Compiler `javac` im
Projektverzeichnis. Die Option -d gibt an, in welches Verzeichnis die
übersetzten Quellfiles geschrieben werden sollen, hier ist es das Verzeichnis: `bin`
(für binaries für *eclipse*, *VS Code*), *IntelliJ* verwendet `out`
(Verzeichnisnamen entsprechend anpassen).

```
# Übersetzen der Dateien package-info und Application.java aus src nach bin:
javac src/application/package-info.java -d bin --module-path lib/modules
javac src/application/Application.java -d bin --module-path lib/modules

# Übersetzen aller .java-Quellfiles aus src nach bin:
javac $(find src -name '*.java') -d bin --module-path lib/modules

find bin                        ; Anzeige der übersetzten Files
```
Anzeige der übersetzten Files in `bin` (bzw. `out`):
```
bin
bin/application
bin/application/Application.class
bin/application/package_info.class
bin/module-info.class
```

&nbsp;

---
## 5. Ausführen des Programms

Die *main()*-Funktion befindet sich in der Klasse `Application.java`.
Das Ausführen des Programms erfolgt mit der `java` VM:

```
java application.Application
```

Ausgabe:

```
Hello, se1.bestellsystem!
```


&nbsp;

---
## 6. Kompilieren der JUnit-Tests

Das Übersetzen der JUnit-Tests erfolgt ebenfalls mit dem Java-Compiler `javac` im
Projektverzeichnis. Die Option -d gibt an, in welches Verzeichnis die übersetzten
Testfiles geschrieben werden: `bin`
(für binaries für *eclipse*, *VS Code*), *IntelliJ* verwendet `out`
(Verzeichnisnamen entsprechend anpassen).

```
javac test/application/Application_0_always_pass_Test.java -d bin --module-path lib/modules

# Übersetzen aller .java-Testfiles aus test nach bin:
javac $(find test -name '*.java') -d bin --module-path lib/modules

find bin                        ; Anzeige der übersetzten Files
```
Anzeige der übersetzten Files in `bin` (bzw. `out`):
```
bin
bin/application
bin/application/Application.class
bin/application/Application_0_always_pass_Test.class
bin/application/package_info.class
bin/module-info.class
```


&nbsp;

---
## 7. Ausführen der JUnit-Tests

JUnit-Tests werden mit dem [Test-Runner](https://mvnrepository.com/artifact/org.junit.platform/junit-platform-console-standalone)
ausgeführt, der angegebene Test-Klassen ausführen kann (`--select-class` oder `-c`)
oder diese selbständig im ${CLASSPATH} sucht (`--scan-classpath`).

Der Test-Runner befindet sich im `lib`-Verzeichnis und wurde vorher als Abhängigkeit installiert.

Die Ausführung der Tests kann in drei Varianten erfolgen mit:

- als Ausführung einer einzelnen Test-Klasse.

- als Ausführung der Test-Klassen, die in der Datei `tests.run` angegeben sind.

- als Ausführung aller Test-Klassen, die im CLASSPATH gefunden werden.

```
java -jar lib/junit-platform-console-standalone-1.9.2.jar -cp "$CLASSPATH" \
          -c application.Application_0_always_pass_Test

java -jar lib/junit-platform-console-standalone-1.9.2.jar -cp "$CLASSPATH" \
          $(grep -v '^#' tests.run)

java -jar lib/junit-platform-console-standalone-1.9.2.jar -cp "$CLASSPATH" \
          --scan-class-path
```

Die Ausgabe der Ergebnisse ist mit nur einer Test-Klasse jeweils gleich:

```
.
+-- JUnit Jupiter [OK]
| '-- Application_0_always_pass_Test [OK]
|   '-- test_001_always_pass() [OK]
+-- JUnit Vintage [OK]
'-- JUnit Platform Suite [OK]

Test run finished after 110 ms
[         4 containers found      ]
[         0 containers skipped    ]
[         4 containers started    ]
[         0 containers aborted    ]
[         4 containers successful ]
[         0 containers failed     ]
[         1 tests found           ]
[         0 tests skipped         ]
[         1 tests started         ]
[         0 tests aborted         ]
[         1 tests successful      ]
[         0 tests failed          ]
```



&nbsp;

---
## 8. Erzeugung der Javadoc

[Javadoc](https://de.wikipedia.org/wiki/Javadoc)
werden mit dem Werkzeug
[javadoc](https://docs.oracle.com/en/java/javase/20/docs/specs/man/javadoc.html)
erzeugt.

```
javadoc --source-path "src" --module-path "lib/modules" -d doc \
    -version -author -noqualifier "java.*:application.*" application datamodel
```

Das Ergebnis befindet sich im Projektverzeichnis unter `doc`:

```
ls -la doc

total 135
drwxr-xr-x  1 svgr2 Kein     0 May 14 20:28 .
drwxr-xr-x+ 1 svgr2 Kein     0 May 14 20:28 ..
-rwxrwxrwx  1 svgr2 Kein  3369 May 14 20:28 allclasses-index.html
-rwxrwxrwx  1 svgr2 Kein  3226 May 14 20:28 allpackages-index.html
-rwxrwxrwx  1 svgr2 Kein   791 May 14 20:28 copy.svg
-rwxrwxrwx  1 svgr2 Kein    39 May 14 20:28 element-list
-rwxrwxrwx  1 svgr2 Kein  9383 May 14 20:28 help-doc.html
-rwxrwxrwx  1 svgr2 Kein  4469 May 14 20:28 index-all.html
-rwxrwxrwx  1 svgr2 Kein  1083 May 14 20:28 index.html          <-- mit Browser öffnen
-rwxrwxrwx  1 svgr2 Kein   536 May 14 20:28 jquery-ui.overrides.css
drwxrwxrwx  1 svgr2 Kein     0 May 14 20:28 legal
-rwxrwxrwx  1 svgr2 Kein   134 May 14 20:28 member-search-index.js
-rwxrwxrwx  1 svgr2 Kein    70 May 14 20:28 module-search-index.js
-rwxrwxrwx  1 svgr2 Kein  3164 May 14 20:28 overview-tree.html
-rwxrwxrwx  1 svgr2 Kein   139 May 14 20:28 package-search-index.js
drwxrwxrwx  1 svgr2 Kein     0 May 14 20:28 resources
drwxrwxrwx  1 svgr2 Kein     0 May 14 20:28 script-dir
-rwxrwxrwx  1 svgr2 Kein  9323 May 14 20:28 script.js
drwxrwxrwx  1 svgr2 Kein     0 May 14 20:28 se1_bestellsystem
-rwxrwxrwx  1 svgr2 Kein 10895 May 14 20:28 search-page.js
-rwxrwxrwx  1 svgr2 Kein  3787 May 14 20:28 search.html
-rwxrwxrwx  1 svgr2 Kein 16316 May 14 20:28 search.js
-rwxrwxrwx  1 svgr2 Kein 28039 May 14 20:28 stylesheet.css
-rwxrwxrwx  1 svgr2 Kein    42 May 14 20:28 tag-search-index.js
-rwxrwxrwx  1 svgr2 Kein   143 May 14 20:28 type-search-index.js
```

Öffnen Sie `index.html` in einem Browser.
