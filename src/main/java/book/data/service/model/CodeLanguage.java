package book.data.service.model;

public enum CodeLanguage {
    C("c", "C"),
    CPP("cpp", "C++"),
    CXX("cxx", "C++"),
    CC("cc", "C++"),
    CP("c++", "C++"),
    CS("cs", "C#"),
    JAVA("java", "Java"),
    PY("py", "Python"),
    JS("js", "JavaScript"),
    TS("ts", "TypeScript"),
    HTML("html", "HTML"),
    CSS("css", "CSS"),
    PHP("php", "PHP"),
    RB("rb", "Ruby"),
    SWIFT("swift", "Swift"),
    GO("go", "Go"),
    RS("rs", "Rust"),
    LUA("lua", "Lua"),
    PL("pl", "Perl"),
    SH("sh", "Shell Script"),
    BAT("bat", "Batch File"),
    PS1("ps1", "PowerShell"),
    SQL("sql", "SQL"),
    JSON("json", "JSON"),
    XML("xml", "XML"),
    YAML("yaml", "YAML"),
    YML("yml", "YAML"),
    H("h", "C Header"),
    HPP("hpp", "C++ Header"),
    ASM("asm", "Assembly"),
    M("m", "Objective-C"),
    VBS("vbs", "VBScript"),
    FS("fs", "F#"),
    FSX("fsx", "F# Script"),
    KT("kt", "Kotlin"),
    KTS("kts", "Kotlin Script"),
    GROOVY("groovy", "Groovy"),
    DART("dart", "Dart"),
    SCALA("scala", "Scala"),
    PAS("pas", "Pascal"),
    LPR("lpr", "Pascal"),
    DPR("dpr", "Pascal"),
    SLN("sln", "Visual Studio Solution"),
    CSPROJ("csproj", "Visual Studio C# Project"),
    VBPROJ("vbproj", "Visual Studio VB Project"),
    VCXPROJ("vcxproj", "Visual Studio C++ Project"),
    MD("md", "Markdown"),
    MAKEFILE("makefile", "Makefile"),
    MK("mk", "Makefile"),
    DOCKERFILE("dockerfile", "Dockerfile"),
    NOTCODE("notcodefileplaceholder", "This represents a file which does not contain code");

    private final String extension;
    private final String language;

    CodeLanguage(String extension, String language) {
        this.extension = extension;
        this.language = language;
    }

    public String getExtension() {
        return extension;
    }

    public String getLanguage() {
        return language;
    }
}
