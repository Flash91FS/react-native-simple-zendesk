# react-native-simple-zendesk.podspec

require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = 'RNSimpleZendesk'
  s.version      = package['version']
  s.summary      = package['description']
  s.homepage     = package['homepage']
  s.license      = package['license']
  s.authors      = package['author']
  s.platform     = :ios, '10'
  s.source       = { git: 'https://github.com/Flash91FS/react-native-simple-zendesk.git', tag: "v#{s.version}" }

  s.source_files = 'ios/*.{h,m,swift}'
  s.requires_arc = true


  s.framework    = 'Foundation'
  s.framework    = 'UIKit'

  s.dependency 'React-Core'
  s.dependency 'ZendeskChatSDK', '~> 2.9'
end


