# Script that sets environment variables with: source .env.sh
#
isWindows=$(uname | grep 'CYGWIN\|MINGW')
[ "$isWindows" ] && sep=';' || sep=':'

# build CLASSPATH in a local variable cp using a system-specific
# separator (Win ";" and Mac, Linux ":")
cp="src"
cp="${cp}${sep}test"
cp="${cp}${sep}bin"
cp="${cp}${sep}lib/junit-platform-console-standalone-1.9.2.jar"
#
# set CLASSPATH environment variable
export CLASSPATH="${cp}"
echo "CLASSPATH=${CLASSPATH}"


# copy .project and .classpath files to project directory, if not present
[ ! -f .project ] \
	&& cp .vscode/.project.init .project \
	&& echo .project file copied

[ ! -f .classpath ] \
	&& cp .vscode/.classpath.init .classpath \
	&& echo .classpath file copied
